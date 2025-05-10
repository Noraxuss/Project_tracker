package project_tracker.application.scene;

import javafx.scene.Parent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseScene {

    private String name;
    private Parent scene;
    private Object controller;

    public void setScene(Parent scene, Object controller) {
        this.scene = scene;
        this.controller = controller;
    }

    public void displaySceneInfo() {
        System.out.println("Scene Name: " + name);
    }
}
