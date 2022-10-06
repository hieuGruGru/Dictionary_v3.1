package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Optional;

public class Controller {

    @FXML
    private Pane titlePane;
    @FXML
    private ImageView minimize, close;
    @FXML
    ListView<String> listView = new ListView<>();
    @FXML
    TextField SearchText = new TextField();
    @FXML
    TextField EText = new TextField();
    @FXML
    TextField VText = new TextField();
    @FXML
    TextField StatusText = new TextField();
    @FXML
    Button buttonSearch;
    @FXML
    Button buttonAdd;
    @FXML
    Button buttonFix;
    @FXML
    Button buttonDel;
    @FXML
    Button buttonSave;
    @FXML
    Button Speech;
    @FXML
    Button buttonReset;

    private Trie newTrie = new Trie();
    private double x, y;
    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);

    protected void init(Stage stage) {
        titlePane.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });
        titlePane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX()-x);
            stage.setY(mouseEvent.getScreenY()-y);
        });
        close.setOnMouseClicked(mouseEvent -> stage.close());
        minimize.setOnMouseClicked(mouseEvent -> stage.setIconified(true));

        try {
            DictionaryManagement.insertFromFile("D:\\java\\Dictionary\\Dictionary_v3.1\\src\\data\\1900w_E-V.txt");
            DictionaryManagement.loadToList(DictionaryManagement.dictionary.trie.root,DictionaryManagement.dictionary.list1);
            showList(listView, DictionaryManagement.dictionary.list1);
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void showAllWord(InputEvent inputEvent) {
        DictionaryManagement.dictionary.list1.clear();
        listView.getItems().clear();
        DictionaryManagement.loadToList(DictionaryManagement.dictionary.trie.root,DictionaryManagement.dictionary.list1);
        showList(listView, DictionaryManagement.dictionary.list1);
    }


    public void lookUp(ActionEvent event) throws IllegalAccessException {
        if (!SearchText.getText().isEmpty()) {
            listView.getItems().clear();
            DictionaryManagement.dictionary.list2.clear();
            String EString = SearchText.getText();
            Trie.TrieNode node = DictionaryManagement.search(EString);
            DictionaryManagement.loadToList(node, DictionaryManagement.dictionary.list2);
            showList(listView, DictionaryManagement.dictionary.list2);
        }
        EText.clear();
        VText.clear();
    }

    public void getMeaning(MouseEvent event) throws IllegalAccessException {
        String word = listView.getSelectionModel().getSelectedItem();
        EText.setText(word);
        VText.setText((DictionaryManagement.search(word)).getMeaning());
        StatusText.clear();
    }

    public void addWord(ActionEvent event) throws IllegalAccessException {
        String word = EText.getText();
        String meaning = VText.getText();
        if (DictionaryManagement.isExist(word,meaning) == 1) {
            DictionaryManagement.insert(word, meaning);
            StatusText.setText("Đã thêm từ mới " + word + " vào từ điển.");
        }
        EText.clear();
        VText.clear();
    }

    public void modifyWord(ActionEvent event) throws IllegalAccessException {
        String word = EText.getText();
        String meaning = VText.getText();
        if (DictionaryManagement.isExist(word, meaning) == 2) {
            DictionaryManagement.insert(word, meaning);
            StatusText.setText("Đã thêm nghĩa mới của từ " + word + " vào từ điển.");
        }
    }

    public void pronounce_E(ActionEvent event) {
        String textPronounce1 = EText.getText();
        Audio.Text_Speech(textPronounce1);
    }

    public void deletelWord(ActionEvent actionEvent) throws IllegalAccessException {
        String word = listView.getSelectionModel().getSelectedItem();
        int index = listView.getSelectionModel().getSelectedIndex();
        confirmAlert.setTitle("Wait a minute");
        confirmAlert.setHeaderText("Bạn có chắc muốn xóa từ " + word + " khỏi từ điển?");
        confirmAlert.setContentText("");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            listView.getItems().remove(index);
            listView.refresh();
            DictionaryManagement.delete(word);
            StatusText.setText("Đã xóa từ " + word + " khỏi từ điển");
            EText.clear();
            VText.clear();
        } else {
            StatusText.setText("May cho mày đấy " + word + " ༼ つ ◕_◕ ༽つ");
        }
    }

    public void saveFile(ActionEvent event) throws FileNotFoundException, UnsupportedEncodingException {

    }

    public void reset(ActionEvent event) {

    }

    public void showList(ListView listView, ArrayList list) {
        for (int i = 0; i < list.size(); i++) {
            listView.getItems().add(list.get(i));
        }
    }

}