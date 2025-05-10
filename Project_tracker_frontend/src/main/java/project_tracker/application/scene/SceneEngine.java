package project_tracker.application.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
import project_tracker.application.utilities.FXMLLoaderUtil;
import project_tracker.application.utilities.onekeytwovaluemap.OneKeyTwoValueMap;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

public class SceneEngine {

    public static final String SCENES_PROPERTIES = "/scene_data/scenes.properties";
    public static final String SCENE_PAIRS_PROPERTIES = "/scene_data/scene_pairs.properties";
    private final Stage stage;
    private final Properties scenePairings;
    private final OneKeyTwoValueMap<String, String, String> sceneMap;
    private final OneKeyTwoValueMap<String, Parent, String> sceneCache;
    private final SplitPane splitPane;

    public SceneEngine(Stage stage) {
        this.stage = stage;
        this.splitPane = new SplitPane();
        // TODO scenefiles is obsolete, remove it after adjusting the code
        this.scenePairings = new Properties();
        this.sceneMap = new OneKeyTwoValueMap<>();
        this.sceneCache = new OneKeyTwoValueMap<>();

        FXMLLoaderUtil.initialize(this);

        loadSceneMap();
        loadScenePairings();
    }

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

    public void initializeStage(String starterScene)  {
        LeftScene.getInstance();
        RightScene.getInstance();

        switchScene(starterScene);

        Scene scene = new Scene(splitPane, 800, 600);

        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

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

    public void switchScene(String sceneName) {
        String sceneSide = sceneMap.get(sceneName).getValue2();
        String otherSceneName = scenePairings.getProperty(sceneName);

        try {
            if (sceneSide.equalsIgnoreCase("left")) {
                updateScenes(sceneName, otherSceneName, LeftScene.getInstance(), RightScene.getInstance());
            } else if (sceneSide.equalsIgnoreCase("right")) {
                updateScenes(sceneName, otherSceneName, RightScene.getInstance(), LeftScene.getInstance());
            } else {
                throw new IllegalArgumentException("Invalid scene side: " + sceneSide);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // TODO this might not be necessary
        splitPane.getItems().clear();
        splitPane.getItems().addAll(LeftScene.getInstance().getScene(),
                RightScene.getInstance().getScene());
    }

    private void updateScenes(String primarySceneName, String secondarySceneName,
                              BaseScene primaryScene, BaseScene secondaryScene) throws IOException {
        primaryScene.setName(primarySceneName);
        primaryScene.setScene(cacheScene(primarySceneName));
        invokeOnSceneLoad(primaryScene);

        secondaryScene.setName(secondarySceneName);
        secondaryScene.setScene(cacheScene(secondarySceneName));
        invokeOnSceneLoad(secondaryScene);
    }

    // resttemplate - HTTP client???


    private void invokeOnSceneLoad(BaseScene instance) {
        try {
            Object controller = instance.getController();
            if (controller == null) {
                throw new NoSuchMethodException("Controller is null");
            }

            Method onSceneLoadMethod = controller.getClass().getDeclaredMethod("onSceneLoad");

            onSceneLoadMethod.invoke(controller);
        } catch (NoSuchMethodException ignored) {
            // Method not found, ignore
        } catch (Exception e) {
            // TODO Create custom exception
            throw new RuntimeException("Failed to invoke onSceneLoad on controller", e);
        }
    }

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

    private void saveController(String sceneName, Parent scene, Object controller) {
        // Save the controller in the corresponding BaseScene
        if (sceneName.equals(LeftScene.getInstance().getName())) {
            LeftScene.getInstance().setScene(scene, controller);
        } else if (sceneName.equals(RightScene.getInstance().getName())) {
            RightScene.getInstance().setScene(scene, controller);
        }
    }

    private FXMLLoader loadScene(String name) throws IOException {
        String fxmlFile = sceneMap.get(name).getValue1();
        if (fxmlFile == null) {
            throw new IllegalArgumentException("Scene not found: " + name);
        }
        return FXMLLoaderUtil.loadFXML(fxmlFile);
    }

    public void clearCache() {
        sceneCache.clear();
    }
}