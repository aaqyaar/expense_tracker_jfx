package com.example.javafx_1;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.net.URL;


public class LoaderController {
    private Pane view;

    public Pane getPage(String fileName) {
        try {
            System.out.println("/main/resources/com.example/expense_tracker/" + fileName + ".fxml");
            URL fileUrl = HelloController.class.getResource(fileName + ".fxml");
            if (fileUrl == null) {
                throw new java.io.FileNotFoundException("Fxml file can't be find");
            }
            view = new FXMLLoader().load(fileUrl);
        } catch (Exception e) {
            System.out.println("No Page" + fileName + "Please check FxmlLoader");
        }
        return view;
    }
}
//public Pane getPage(String fileName) {
//    try {
//        System.out.println(fileName);
//        URL fileUrl = HelloController.class.getResource( fileName + ".fxml");
//        if (fileUrl == null) {
//            throw new java.io.FileNotFoundException("Fxml file can't be found");
//        }
//        view = new FXMLLoader().load(fileUrl);
//        if (view == null) {
//            throw new NullPointerException("FXMLLoader returned a null view");
//        }
//    } catch (Exception e) {
//        System.out.println("Error loading page " + fileName + ": " + e.getMessage());
//        e.printStackTrace();
//    }
//    return view;
//}
//}
