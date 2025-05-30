package project_tracker_frontend.application.controller.controller_utilities;

import project_tracker_frontend.application.controller.BaseLayoutController;

public interface BaseLayoutControllerAware {

    /**
     * This method is used to set the layout controller.
     */
    void setLayoutController(BaseLayoutController layoutController);
}
