package project_tracker_frontend.application.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.controlsfx.control.HiddenSidesPane;
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

public class SceneEngine implements BaseLayoutControllerAware {

    public static final String SCENES_PROPERTIES = "/scene_data/scenes.properties";
    public static final String SCENE_PAIRS_PROPERTIES = "/scene_data/scene_pairs.properties";
    private final Stage stage;
    private final Properties scenePairings;
    @Getter
    private final OneKeyTwoValueMap<String, String, String> sceneMap;
    @Getter
    private final OneKeyTwoValueMap<String, Parent, String> sceneCache;
    @Getter
    private BaseLayoutController baseLayoutController;

    private static final Logger logger = LoggerFactory.getLogger(SceneEngine.class);

    public SceneEngine(Stage stage) {
        this.stage = stage;
        // TODO scene files is obsolete, remove it after adjusting the code
        this.scenePairings = new Properties();
        this.sceneMap = new OneKeyTwoValueMap<>();
        this.sceneCache = new OneKeyTwoValueMap<>();

        FXMLLoaderUtil.initialize(this);

        loadSceneMap();
        loadScenePairings();
    }

    /**
     * Loads the scene pairings from the properties file.
     * The properties file should be in the format:
     * sceneName=otherSceneName
     */
    private void loadScenePairings() {
        try (InputStream input = getClass().getResourceAsStream(SCENE_PAIRS_PROPERTIES)) {
            if (input == null) {
                throw new IOException("Unable to find scenes.properties");
            }
            scenePairings.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the scene map from the properties file.
     * The properties file should be in the format:
     * sceneName=fxmlFilePath=sceneSide
     */
    private void loadSceneMap() {
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
                } else {
                    throw new IllegalArgumentException("Invalid format in scenes.properties for key: " + key);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the stage with the given starter scene.
     * It sets up the center and sidebar scenes, initializes the hidden pane,
     * and switches to the starter scene.
     * The hidden pane is used to manage the visibility of the center and sidebar scenes.
     */
    public void initializeStage(String starterScene, String loading) {
        CenterScene.getInstance();
        SideBarScene.getInstance();
        switchScene(starterScene);

        // Load the base layout controller
        Scene scene = new Scene(sceneCache.get("base").getValue1(), 800, 600);
        stage.setScene(scene);

        switchScene(loading);
        stage.setResizable(true);
        stage.show();
    }

    /**
     * Switches the scene based on the given scene name.
     * It checks the scene placement (side, center, extra) and updates the scenes accordingly.
     * If the scene is not found, it throws an IllegalArgumentException.
     */
    public void switchScene(String sceneName) {
        String scenePlacement = sceneMap.get(sceneName).getValue2();
        logger.info("Switching scene {}", sceneName);
        // Validate scene name and placement
        try {
            if (scenePlacement.equalsIgnoreCase("side")) {
                updateScenes(sceneName, SideBarScene.getInstance());
            } else if (scenePlacement.equalsIgnoreCase("center")) {
                updateScenes(sceneName, CenterScene.getInstance());
            } else if (scenePlacement.equalsIgnoreCase("extra")) {
                updateScenes(sceneName, new ExtraScene());
            } else if (scenePlacement.equalsIgnoreCase("all")) {
                cacheScene(sceneName);
            } else {
                throw new IllegalArgumentException("Invalid scene side: " + scenePlacement);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the scenes based on the scene name and FXML file path.
     * It sets the name, caches the scene, and updates the hidden pane accordingly.
     * It also invokes the onSceneLoad method on the controller of the BaseScene instance.
     */
    private void updateScenes(String sceneName, BaseScene instance) throws IOException {
        instance.setName(sceneName);
        // is this redundant?
        Parent scene = cacheScene(sceneName);
        instance.setScene(scene, instance.getController());

        // Update the hidden pane based on the scene name
        if (sceneName.equals(CenterScene.getInstance().getName())) {
            baseLayoutController.setContentToContentPane(scene);
        } else if (sceneName.equals(SideBarScene.getInstance().getName())) {
            baseLayoutController.setLeftSideBarToLeftSide(scene);
        } else if (sceneName.equals(instance.getName())) {
            makeExtraStages(scene);
        }

        // Invoke the onSceneLoad method on the controller
        invokeOnSceneLoad(instance);
    }

    /**
     * Creates and shows an extra stage for the given scene.
     * The extra stage is used for scenes that are not the main center or sidebar scenes.
     * like: "Settings", "About", "Login", "Register", "Create Project", "Create Task", etc.
     */
    private void makeExtraStages(Parent scene) {
        ExtraScene extraScene = new ExtraScene();
        extraScene.initializeExtraStage(scene, stage);
        Stage extraStage = extraScene.getExtraStage();
        invokeOnSceneLoad(extraScene);
        extraStage.showAndWait();
    }

    /**
     * Invokes the onSceneLoad method on the controller of the BaseScene instance.
     * This method uses reflection to find and invoke the method.
     * If the method is not found, it will be ignored.
     * If an error occurs during invocation, a RuntimeException will be thrown.
     */
    private void invokeOnSceneLoad(BaseScene instance) {
        // This method uses reflection to invoke the onSceneLoad
        // method on the controller of the BaseScene instance.
        try {
            Object controller = instance.getController();
            if (controller == null) {
                throw new NoSuchMethodException("Controller is null");
            }
            // Check if the controller has the onSceneLoad method
            Method onSceneLoadMethod = controller.getClass().getDeclaredMethod("onSceneLoad");

            // Invoke the onSceneLoad method
            onSceneLoadMethod.invoke(controller);
        } catch (NoSuchMethodException ignored) {
            // Method not found, ignore
        } catch (Exception e) {
            // TODO Create custom exception
            throw new RuntimeException("Failed to invoke onSceneLoad on controller", e);
        }
    }

    /**
     * Caches the scene by its name.
     * If the scene is already cached, it returns the cached scene.
     * Otherwise, it loads the scene from the FXML file and caches it.
     */
    private Parent cacheScene(String sceneName) throws IOException {

        Parent scene;
        if (sceneCache.containsKey(sceneName)) {
            scene = sceneCache.get(sceneName).getValue1();
        } else {

            FXMLLoader loader = loadScene(sceneName);

            scene = loader.load();
            sceneCache.put(sceneName, scene, sceneMap.get(sceneName).getValue1());

            Object controller = loader.getController();

            saveController(sceneName, scene, controller);
        }
        return scene;
    }

    /**
     * Saves the controller in the corresponding BaseScene instance.
     * This method is used to save the controller after loading the scene.
     * It checks which scene is being loaded and saves the controller accordingly.
     */
    private void saveController(String sceneName, Parent scene, Object controller) {
        // Save the controller in the corresponding BaseScene
        if (sceneName.equals(CenterScene.getInstance().getName())) {
            CenterScene.getInstance().setScene(scene, controller);
        } else if (sceneName.equals(SideBarScene.getInstance().getName())) {
            SideBarScene.getInstance().setScene(scene, controller);
        }
    }

    /**
     * Loads the FXML file for the given scene name.
     * It uses the FXMLLoaderUtil to load the FXML file.
     * If the scene name is not found, it throws an IllegalArgumentException.
     */
    private FXMLLoader loadScene(String name) throws IOException {
        String fxmlFile = sceneMap.get(name).getValue1();
        if (fxmlFile == null) {
            throw new IllegalArgumentException("Scene not found: " + name);
        }
        return FXMLLoaderUtil.loadFXML(fxmlFile, name);
    }

    @Override
    public void setLayoutController(BaseLayoutController layoutController) {
        this.baseLayoutController = layoutController;
    }


}