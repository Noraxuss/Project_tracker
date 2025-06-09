package project_tracker_frontend.application.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project_tracker_frontend.application.controller.BaseLayoutController;
import project_tracker_frontend.application.controller.controller_utilities.BaseLayoutControllerAware;
import project_tracker_frontend.application.utilities.FXMLLoaderUtil;
import project_tracker_frontend.application.utilities.onekeytwovaluemap.OneKeyTwoValueMap;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * SceneEngine handles all scene management:
 * - Scene loading (FXML)
 * - Scene caching
 * - Controller injection
 * - Switching scenes dynamically at runtime
 */
public class SceneEngine implements BaseLayoutControllerAware {

    public static final String SCENES_PROPERTIES = "/scene_data/scenes.properties";

    private final Stage stage;

    @Getter
    private final OneKeyTwoValueMap<String, String, String> sceneMap;

    @Getter
    private final OneKeyTwoValueMap<String, Parent, String> sceneCache;

    @Getter
    private BaseLayoutController baseLayoutController;

    private static final Logger logger = LoggerFactory.getLogger(SceneEngine.class);

    public SceneEngine(Stage stage) {
        this.stage = stage;
        this.sceneMap = new OneKeyTwoValueMap<>();
        this.sceneCache = new OneKeyTwoValueMap<>();

        logger.debug("Initializing FXMLLoaderUtil and loading scene map.");
        FXMLLoaderUtil.initialize(this);
        loadSceneMap();
    }

    /**
     * Loads the scene map from the properties file (sceneName=fxmlFilePath=sceneSide).
     */
    private void loadSceneMap() {
        logger.debug("Loading scenes from properties file.");
        try (InputStream input = getClass().getResourceAsStream(SCENES_PROPERTIES)) {
            if (input == null) {
                throw new IOException("Unable to find scenes.properties");
            }

            Properties sceneFiles = new Properties();
            sceneFiles.load(input);

            for (String key : sceneFiles.stringPropertyNames()) {
                String[] values = sceneFiles.getProperty(key).split("=");
                if (values.length == 2) {
                    sceneMap.put(key, values[0], values[1]);
                    logger.debug("Scene registered: {} -> {} ({})", key, values[0], values[1]);
                } else {
                    throw new IllegalArgumentException("Invalid format for key: " + key);
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            logger.error("Error loading scene map: {}", e.getMessage());
            throw new RuntimeException("Failed to load scene map", e);
        }
    }

    /**
     * Initializes the primary stage with a base scene and a starter scene.
     */
    public void initializeStage(String starterScene, String loading) {
        logger.info("Initializing stage with starter scene: {}", starterScene);

        CenterScene.getInstance();
        SideBarScene.getInstance();

        switchScene(starterScene);

        Scene scene = new Scene(sceneCache.get("base").getValue1(), 800, 600);
        stage.setScene(scene);

        switchScene(loading);

        stage.setResizable(true);
        stage.show();

        logger.debug("Stage initialized and shown.");
    }

    /**
     * Switches the displayed scene based on its logical placement.
     */
    public void switchScene(String sceneName) {
        logger.info("Switching to scene: {}", sceneName);

        String scenePlacement = sceneMap.get(sceneName).getValue2();
        logger.debug("Scene placement for {} is '{}'", sceneName, scenePlacement);

        try {
            switch (scenePlacement.toLowerCase()) {
                case "side" -> updateScenes(sceneName, SideBarScene.getInstance());
                case "center" -> updateScenes(sceneName, CenterScene.getInstance());
                case "extra" -> updateScenes(sceneName, new ExtraScene());
                case "all" -> cacheScene(sceneName, null); // pre-cache only
                default -> throw new IllegalArgumentException("Invalid scene placement: " + scenePlacement);
            }
        } catch (IOException e) {
            logger.error("Error switching scene {}: {}", sceneName, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Core scene update logic, handles placement, caching, and controller setup.
     */
    private void updateScenes(String sceneName, BaseScene instance) throws IOException {
        logger.info("Updating scene: {}", sceneName);

        instance.setName(sceneName);
        logger.debug("Scene instance set to: {}", instance.getClass().getSimpleName());

        Parent scene = cacheScene(sceneName, instance);
        instance.setScene(scene, instance.getController());

        // Update visual pane placement
        if (sceneName.equals(CenterScene.getInstance().getName())) {
            baseLayoutController.setContentToContentPane(scene);
        } else if (sceneName.equals(SideBarScene.getInstance().getName())) {
            baseLayoutController.setLeftSideBarToLeftSide(scene);
        } else if (sceneName.equals(instance.getName())) {
            makeExtraStages(instance, sceneName, scene);
        }

        invokeOnSceneLoad(instance);
    }

    /**
     * Opens an additional window (stage) for an "extra" scene.
     */
    private void makeExtraStages(BaseScene extraScene, String sceneName, Parent parentScene) throws IOException {
        logger.debug("Creating extra stage for scene: {}", sceneName);

        ExtraScene scene = (ExtraScene) extraScene;
        scene.initializeExtraStage(parentScene, stage);

        Stage extraStage = scene.getExtraStage();

        invokeOnSceneLoad(scene);

        logger.info("Showing ExtraScene '{}' titled '{}'", scene.getName(), extraStage.getTitle());
        extraStage.showAndWait();
    }

    /**
     * Uses reflection to invoke 'onSceneLoad' on a controller, if defined.
     */
    private void invokeOnSceneLoad(BaseScene instance) {
        logger.debug("Invoking onSceneLoad for: {}", instance.getName());

        try {
            Object controller = instance.getController();
            if (controller == null) {
                throw new NoSuchMethodException("Controller is null for scene: " + instance.getName());
            }

            Method onSceneLoadMethod = controller.getClass().getDeclaredMethod("onSceneLoad");
            onSceneLoadMethod.setAccessible(true);
            onSceneLoadMethod.invoke(controller);
        } catch (NoSuchMethodException e) {
            logger.debug("No onSceneLoad method found for controller: {}", instance.getController());
        } catch (Exception e) {
            logger.error("Error invoking onSceneLoad for {}: {}", instance.getName(), e.getMessage());
            throw new RuntimeException("Failed to invoke onSceneLoad", e);
        }
    }

    /**
     * Returns a cached scene or loads it freshly.
     * ExtraScenes are always reloaded to avoid reuse conflicts.
     */
    private Parent cacheScene(String sceneName, BaseScene instance) throws IOException {
        Parent scene;

        if (instance instanceof ExtraScene) {
            logger.debug("Loading ExtraScene (non-cached): {}", sceneName);
            FXMLLoader loader = loadScene(sceneName);
            scene = loader.load();
            saveController(sceneName, scene, loader.getController(), instance);
            return scene;
        }

        if (sceneCache.containsKey(sceneName)) {
            logger.debug("Using cached scene: {}", sceneName);
            scene = sceneCache.get(sceneName).getValue1();
        } else {
            logger.debug("Scene not cached. Loading: {}", sceneName);
            FXMLLoader loader = loadScene(sceneName);
            scene = loader.load();
            sceneCache.put(sceneName, scene, sceneMap.get(sceneName).getValue1());
            saveController(sceneName, scene, loader.getController(), instance);
        }

        return scene;
    }

    /**
     * Stores the loaded scene/controller pair into the appropriate scene object.
     */
    private void saveController(String sceneName, Parent scene, Object controller, BaseScene instance) {
        if (instance == null || controller == null) {
            logger.debug("Skipping saveController: instance or controller is null.");
            return;
        }

        logger.debug("Saving controller for scene: {}", sceneName);

        if (sceneName.equals(CenterScene.getInstance().getName())) {
            CenterScene.getInstance().setScene(scene, controller);
        }
        if (sceneName.equals(SideBarScene.getInstance().getName())) {
            SideBarScene.getInstance().setScene(scene, controller);
        }
        if (sceneName.equals(instance.getName())) {
            instance.setScene(scene, controller);
        }
    }

    /**
     * Helper to load an FXML scene by name from the sceneMap.
     */
    private FXMLLoader loadScene(String name) throws IOException {
        logger.debug("Loading FXML for scene: {}", name);
        String fxmlFile = sceneMap.get(name).getValue1();

        if (fxmlFile == null) {
            throw new IllegalArgumentException("Scene not found in map: " + name);
        }

        return FXMLLoaderUtil.loadFXML(fxmlFile, name);
    }

    /**
     * Sets the shared BaseLayoutController used to update UI containers.
     */
    @Override
    public void setLayoutController(BaseLayoutController layoutController) {
        logger.debug("Base layout controller injected.");
        this.baseLayoutController = layoutController;
    }
}
