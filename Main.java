import javafx.application.Application;
import javafx.scene.Scene;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import java.util.ArrayList;
/*
 * Application for Resume building.
 * This client program contains an application where the use can modify a database (file: db.xml), 
 * where you can add, delete, edit, print and search for records to have choose which records you would
 * like to resumes for. In the application the "Export" menu item allows for this such action where you can choose
 * the records you want to have a resume for and the header you would want at the top of each resume, and it will make 
 * an html file with all the resumes. 
 *
 * This file is for the multiple screens of the application (this class uses JavaFX libraries)
 * Your recursive functions should work with an unmodified version of this file.
 *
 * @author Sihguat Torres
 * @version 08/17/2019
 * - modified for 1 version, including new Export capibility
 */

public class Main extends Application{
    /*
    * All of these variables are global variables since they are used for temporary prompts when the
    * application asks for certain fields from the user.
    *
    */

	Stage window; 
	ListView<String> listSearches;
	BorderPane layout;
    TextField titleInput,orgInput;
	//Name
	TextField nameInput = new TextField();
	//Position
	TextField positionInput = new TextField();
	//Education
	 TextField schoolInput = new TextField();
     TextField degreeInput = new TextField();
     TextField majorInput = new TextField();
     TextField schoolInput2 = new TextField();
     TextField degreeInput2 = new TextField();
     TextField majorInput2 = new TextField();
	//Bio
	TextArea bioInput = new TextArea();
	//Certificates
	TableView<Certificate> tableCertificates = new TableView<Certificate>();
    //Image filePath
    TextField imageInput = new TextField();
	//Expertise and Skills
	ListView<String> listExpertise = new ListView<String>();
    ListView<String> listSkills = new ListView<String>();
	//Work
	TableView<Work> tableWork = new TableView<Work>();
	TextField positionInputW, org2Input, startMInput, startYInput, endMInput, endYInput;
	TextArea descriptionInput;

    // multiple pages
	Scene scene, scene1,scene2, scenePrint, sceneExport;
	DB db = new DB();

    /*
     * Main method and launch(args) is necessary to 
     * launch the application from JavaFX.
     *
     */
	public static void main(String [] args){
		launch(args);
	}
    /*
     * This a start method inheritated from the JavaFX library
     */
	@Override
	public void start(Stage primaryStage) throws Exception{
		window = primaryStage;
		window.setTitle("Application");
		mainPage();
		window.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
		});

	}
    /*
     * This method is for the main page of the application with the multiple menu-items 
     *  Menu Items
     *  - Database
     *      - Print Records
     *      - Add Records
     *      - Delete Records
     *      - Edit Records
     *      - Search Records
     *  - Export
     * This page also displays the current records, their record numbers and name
     *
     */
	public void mainPage(){
		//Menu
		Menu dataBaseMenu = new Menu("Database");


		//Menu items 
		MenuItem printItem = new MenuItem("Print Record");
		printItem.setOnAction(e -> printRecordPage());
		dataBaseMenu.getItems().add(printItem);

		MenuItem addItem = new MenuItem("Add Record");
		addItem.setOnAction(e -> addRecordPage());
		dataBaseMenu.getItems().add(addItem);

		MenuItem deleteItem = new MenuItem("Delete Record");
		deleteItem.setOnAction(e -> deleteRecordPage());
		dataBaseMenu.getItems().add(deleteItem);

		MenuItem editItem = new MenuItem("Edit Record");
		editItem.setOnAction(e -> editRecordPage());
		dataBaseMenu.getItems().add(editItem);

		dataBaseMenu.getItems().add(new SeparatorMenuItem());

		MenuItem searchItem = new MenuItem("Search Keyword");
		searchItem.setOnAction(e -> searchRecordPage());
		dataBaseMenu.getItems().add(searchItem);

        //Menu
        Menu exportMenu = new Menu("Export");

        //MenuItem
        MenuItem exportResume = new Menu("Export");
        exportResume.setOnAction(e -> exportRecordsPage());
        exportMenu.getItems().add(exportResume);

		//Main MenuBar
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(dataBaseMenu, exportMenu);

        //Actual Content

        ListView listRecords = new ListView<>();
        String [] stringRecords = db.getRecNumbers();
        listRecords.getItems().clear();
        for(String stringRecord: stringRecords) listRecords.getItems().add(stringRecord);

        Label infoLabel = new Label("Records Numbers: ");
        Label numberLabel = new Label(stringRecords.length + " records.");
        VBox content = new VBox(10);
        content.getChildren().addAll(infoLabel, listRecords, numberLabel);

		layout = new BorderPane();
		layout.setTop(menuBar);
        layout.setCenter(content);
		//
		scene = new Scene(layout, 1200,800);
		scene.getStylesheets().add("MainPage.css");
		window.setScene(scene);
		window.show();
	}
    /*
     * This method is for the export page of the application. This page prompts for 
     * the records you want to make resumes for. This page also prompts for the header you would
     * like to include at the top of every resume.
     *
     */
    public void exportRecordsPage(){
        //Label
        Label numLabel = new Label("What Records would you like to have on the document");
        //Input box
        TextField numInput = new TextField();
        numInput.setPromptText("Insert Record Number.");
        //list of displayed record numbers that want to be exported 
        ListView listExports = new ListView<>();

        //Header Label
        Label headerLabel = new Label("What text would you like on the proposal header?");
        //Header Input 
        TextField headerInput = new TextField();
        headerInput.setPromptText("Insert RFP description.");

        //buttons to add and delete entry 
        Button addNum = new Button("Add");
        addNum.setOnAction(e -> {
            listExports.getItems().add(numInput.getText());
            numInput.clear();
        });
        Button deleteNum = new Button("Delete");
        deleteNum.setOnAction(e -> {
            ObservableList<String> numsSelected, allNums;
            allNums = listExports.getItems();
            numsSelected = listExports.getSelectionModel().getSelectedItems();

            numsSelected.forEach(allNums::remove);
        });

        //box containing buttons and text prompt
        HBox hBoxExports = new HBox();
        hBoxExports.setPadding(new Insets(10,10,10,10));
        hBoxExports.setSpacing(10);
        hBoxExports.getChildren().addAll(numInput, addNum, deleteNum);

        //label having warning 
        Label infoLabel = new Label("Make sure each Record has wanted content, before clicking Submit.");

        //button to submit entries 
        Button submitExport = new Button("Submit");
        submitExport.setOnAction(e-> {
            ArrayList<Integer> numbers = new ArrayList<Integer>();
            for(Object numObj: listExports.getItems()){
                String numString = numObj.toString();
                try{
                    numbers.add(Integer.parseInt(numString));
                }
                catch(Exception ex){
                    mainPage();
                }
            }
            listExports.getItems().clear();
            db.exportRecords(numbers, headerInput.getText());
            mainPage();
        });
        //layout of window 
        VBox layout = new VBox();
        layout.getChildren().addAll(numLabel, hBoxExports,listExports, headerLabel, headerInput, 
                  infoLabel, submitExport);

        sceneExport = new Scene(layout, 1200, 800);
        window.setScene(sceneExport);
        window.show();


    }
    /*
     * This method is for the print page of the application. This page prompts which 
     * record you would wish to find out further information about. Once you print it, 
     * it returns all information associated with that Record.
     *
     */
	public void printRecordPage(){
        Label numberPLabel = new Label("What Record would you like to view");
        TextField numberPInput = new TextField();
        numberPInput.setPromptText("Insert Record Number.");

        ListView recordP = new ListView<>();
        //ADD and DELETE BUTTONS
        Button printButton = new Button("Print");
        printButton.setOnAction(e -> {
            String [] recordParts = db.printRecord(Integer.parseInt(numberPInput.getText()));
            recordP.getItems().clear();
            if(recordParts!=null){
            for(String part: recordParts){
                recordP.getItems().add(part);
            }
            }   
            
            numberPInput.clear();
        });
        
        Button goBackPButton = new Button("Go Back");
        goBackPButton.setOnAction(e -> {
            mainPage();
        });

        VBox layout = new VBox(10);
        HBox buttonPBox = new HBox();
        buttonPBox.setPadding(new Insets(10,10,10,10));
        buttonPBox.setSpacing(10);
        buttonPBox.getChildren().addAll(goBackPButton, printButton);
        layout.getChildren().addAll(numberPLabel, numberPInput, buttonPBox, recordP);

        scenePrint = new Scene(layout, 1200, 800);
        window.setScene(scenePrint);
        window.show();
        
	}
    /*
     * This method is for the first addpage of the application. The adding functionality
     * includes multiple prompts so this is why it is split up into two pages. 
     * This page prompts for...
     * - Name
     * - Position
     * - Education
     * - Bio 
     * - Certificates
     * - Image
     * After these prompts are inserted you can continue to the next page which will have the 
     * rest of the prompts.
     *
     */
	@SuppressWarnings("unchecked")
	public void addRecordPage(){
	
		//GridPane with 10px padding around edge
		scene2=addRecordPage2();
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.getColumnConstraints().add(new ColumnConstraints(170)); // column 0 is 100 wide
     	grid.getColumnConstraints().add(new ColumnConstraints(600));
        grid.setVgap(8);
        grid.setHgap(10);

        //Name Label - constrains use (child, column, row)
        Label introLabel = new Label("Fill out the following fields.");
        GridPane.setConstraints(introLabel, 0, 1);
        Label introLabel2 = new Label("Click 'Add Record' when done");
        GridPane.setConstraints(introLabel2, 1, 1);

        //Name Label - constrains use (child, column, row)
        Label nameLabel = new Label("Name:");
        GridPane.setConstraints(nameLabel, 0, 2);

        //Name Input
        nameInput.setPromptText("Micheal Scott");
        GridPane.setConstraints(nameInput, 1, 2);

        //Position Label
        Label positionLabel = new Label("Position:");
        GridPane.setConstraints(positionLabel, 0, 3);

        //Position Input
        positionInput.setPromptText("Ex.  Assistant to the Regional Manager");
        GridPane.setConstraints(positionInput, 1, 3);

        //Education Label
        Label educationLabel = new Label("Education:");
        educationLabel.setId("bold-label");
        GridPane.setConstraints(educationLabel, 0, 4);

        //School Label
        Label schoolLabel = new Label("School:");
        GridPane.setConstraints(schoolLabel, 0, 5);

        //School Input
        schoolInput.setPromptText("Ex. Stanford University");
        GridPane.setConstraints(schoolInput, 1, 5);

        //Degree Label
        Label degreeLabel = new Label("Degree:");
        GridPane.setConstraints(degreeLabel, 0, 6);

        //Degree Input
        degreeInput.setPromptText("Ex. BA or BS");
        GridPane.setConstraints(degreeInput, 1, 6);

        //Major Label
        Label majorLabel = new Label("Major:");
        GridPane.setConstraints(majorLabel, 0, 7);

        //Major Input
        majorInput.setPromptText("Ex. Computer Science");
        GridPane.setConstraints(majorInput, 1, 7);

        //Education Label
        Label educationLabel2 = new Label("Education (Optional):");
        educationLabel2.setId("bold-label");
        GridPane.setConstraints(educationLabel2, 0, 8);

        //School Label
        Label schoolLabel2 = new Label("School:");
        GridPane.setConstraints(schoolLabel2, 0, 9);

        //School Input
        schoolInput2.setPromptText("Ex. Stanford University");
        GridPane.setConstraints(schoolInput2, 1, 9);

        //Degree Label
        Label degreeLabel2 = new Label("Degree:");
        GridPane.setConstraints(degreeLabel2, 0, 10);

        //Degree Input
        degreeInput2.setPromptText("Ex. BA or BS");
        GridPane.setConstraints(degreeInput2, 1, 10);

        //Major Label
        Label majorLabel2 = new Label("Major:");
        GridPane.setConstraints(majorLabel2, 0, 11);

        //Major Input
        majorInput2.setPromptText("Ex. Computer Science");
        GridPane.setConstraints(majorInput2, 1, 11);

         //Bio Label
        Label bioLabel = new Label("Bio:");
        GridPane.setConstraints(bioLabel, 0, 12);

        //Bio Input
        bioInput.setPromptText("Ex. Micheal Scott has over 20 years...");
        bioInput.setPrefColumnCount(5);
        bioInput.setPrefRowCount(5);
        bioInput.setScrollLeft(10);
        bioInput.setWrapText(true);
        GridPane.setConstraints(bioInput, 1, 12);

        //Table for Certificates 
        //org column
        TableColumn<Certificate, String> orgColumn = new TableColumn<>("Org");
        orgColumn.setMinWidth(400);
        orgColumn.setCellValueFactory(new PropertyValueFactory<>("org"));

        //title column
        TableColumn<Certificate, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setMinWidth(300);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        //org input
        orgInput = new TextField();
        orgInput.setPromptText("Organization");
        orgInput.setMinWidth(100);

        //title input
        titleInput = new TextField();
        titleInput.setPromptText("Title"); 

        //Certificate Buttons
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addButtonClickedC());
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteButtonClickedC());

        //putting buttons and inputs in one horizontal box
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(orgInput, titleInput, addButton, deleteButton);

        tableCertificates.getColumns().addAll(orgColumn, titleColumn);

         // Label
        Label certificateLabel = new Label("Certificates:");
        
        GridPane.setConstraints(certificateLabel, 0, 17);
        GridPane.setConstraints(hBox, 1, 17);
        GridPane.setConstraints(tableCertificates,1,18);
        /*
        *
        * This will be prompting for file name of image for the record
        *
        *
        *
        */
        //IMG Label
        Label imageLabel = new Label("Image FileName:");
        GridPane.setConstraints(imageLabel, 0, 20);

        //IMG Input
        imageInput.setPromptText("Ex. Stanford University");
        GridPane.setConstraints(imageInput, 1, 20);

        Button nextButton = new Button("Save and Next");
        nextButton.setOnAction(e -> {
            window.setScene(scene2);
        });
        GridPane.setConstraints(nextButton, 1, 21);
        //setting up grid and changing scene
        grid.getChildren().addAll(introLabel, introLabel2, nameLabel, nameInput, positionLabel, positionInput, educationLabel, schoolLabel,
        	schoolInput, degreeLabel, degreeInput, majorLabel, majorInput, educationLabel2, schoolLabel2,
        	schoolInput2, degreeLabel2, degreeInput2, majorLabel2, majorInput2, bioLabel, bioInput,certificateLabel,
        	hBox, tableCertificates,imageLabel, imageInput, nextButton);

        //

        scene1 = new Scene(grid, 1200, 800);
        scene1.getStylesheets().add("AddPage.css");
        window.setScene(scene1);
        window.show();
	}
    /*
     * This method is for the second addpage of the application. 
     * This page prompts for...
     * - Expertise
     * - Skills
     * - Work
     * After these prompts are inserted you can submit, after it will save the record
     * to the database and then go back to the mainpage.
     *
     */
	@SuppressWarnings("unchecked")
	public Scene addRecordPage2(){

		//GridPane with 10px padding around edge
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.getColumnConstraints().add(new ColumnConstraints(170)); // column 0 is 100 wide
     	grid.getColumnConstraints().add(new ColumnConstraints(600));
        grid.setVgap(8);
        grid.setHgap(10);
		/*
        *
        *
        *
        *Prompt for Expertise
        *
        *
        */
        //Label
        Label expertiseLabel = new Label("Expertises:");
        GridPane.setConstraints(expertiseLabel, 0, 0);

        //expertise input
        TextField expertiseInput = new TextField();
        expertiseInput.setPromptText("Ex. Project Management");

        //ADD and DELETE BUTTONS
        Button addButtonEx = new Button("Add");
        addButtonEx.setOnAction(e -> {
        	listExpertise.getItems().add(expertiseInput.getText());
        	expertiseInput.clear();
        });
        Button deleteButtonEx = new Button("Delete");

        deleteButtonEx.setOnAction(e -> {
        	ObservableList<String> expertisesSelected, allExpertises;
        	allExpertises = listExpertise.getItems();
        	expertisesSelected = listExpertise.getSelectionModel().getSelectedItems();

        	expertisesSelected.forEach(allExpertises::remove);
        });

        //box containing buttons and text prompt
        HBox hBoxExpertise = new HBox();
        hBoxExpertise.setPadding(new Insets(10,10,10,10));
        hBoxExpertise.setSpacing(10);
        hBoxExpertise.getChildren().addAll(expertiseInput, addButtonEx, deleteButtonEx);

        //implement into GripPlane
        GridPane.setConstraints(hBoxExpertise, 1, 0);
        GridPane.setConstraints(listExpertise,1,1);
        
        /*
        *
        *
        *
        *Prompt for Skills
        *
        *
        */
        //Label
        Label skillLabel = new Label("Skills:");
        GridPane.setConstraints(skillLabel, 0, 2);

        //expertise input
        TextField skillsInput = new TextField();
        skillsInput.setPromptText("Ex. Primavera");

        //ADD and DELETE BUTTONS
        Button addButtonSk = new Button("Add");
        addButtonSk.setOnAction(e -> {
        	listSkills.getItems().add(skillsInput.getText());
        	skillsInput.clear();
        });
        Button deleteButtonSk = new Button("Delete");
        deleteButtonSk.setOnAction(e -> {
        	ObservableList<String> skillsSelected, allSkills;
        	allSkills = listSkills.getItems();
        	skillsSelected = listSkills.getSelectionModel().getSelectedItems();

        	skillsSelected.forEach(allSkills::remove);
        });

        //box containing buttons and text prompt
        HBox hBoxSkill = new HBox();
        hBoxSkill.setPadding(new Insets(10,10,10,10));
        hBoxSkill.setSpacing(10);
        hBoxSkill.getChildren().addAll(skillsInput, addButtonSk, deleteButtonSk);

        //implement into GripPlane
        GridPane.setConstraints(hBoxSkill, 1, 2);
        GridPane.setConstraints(listSkills,1,3);

        /*
        *
        *
        *Table for the Work
        *
        *
        *
        */ 
        // positioncolumn
        TableColumn<Work, String> positionColumn = new TableColumn<>("Position");
        positionColumn.setMinWidth(150);
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));

        //title column
        TableColumn<Work, String> org2Column = new TableColumn<>("Org");
        org2Column.setMinWidth(150);
        org2Column.setCellValueFactory(new PropertyValueFactory<>("org"));

        // start Monthcolumn
        TableColumn<Work, String> startMColumn = new TableColumn<>("StartMonth");
        startMColumn.setMinWidth(100);
        startMColumn.setCellValueFactory(new PropertyValueFactory<>("startMonth"));

        //start Year column
        TableColumn<Work, String> startYColumn = new TableColumn<>("StartYear");
        startYColumn.setMinWidth(75);
        startYColumn.setCellValueFactory(new PropertyValueFactory<>("startYear"));

        // end monthcolumn
        TableColumn<Work, String> endMColumn = new TableColumn<>("EndMonth");
        endMColumn.setMinWidth(100);
        endMColumn.setCellValueFactory(new PropertyValueFactory<>("endMonth"));

        //end Monthcolumn
        TableColumn<Work, String> endYColumn = new TableColumn<>("EndYear");
        endYColumn.setMinWidth(75);
        endYColumn.setCellValueFactory(new PropertyValueFactory<>("endYear"));

         //end Monthcolumn
        TableColumn<Work, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setMinWidth(500);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
      
        //INPUTS
        positionInputW = new TextField();
        positionInputW.setPromptText("Position");

        org2Input = new TextField();
        org2Input.setPromptText("Employer");

        startMInput = new TextField();
        startMInput.setPromptText("Start Month");

        startYInput = new TextField();
        startYInput.setPromptText("Start Year");

        endMInput = new TextField();
        endMInput.setPromptText("End Month");

        endYInput = new TextField();
        endYInput.setPromptText("End Year");
         
        descriptionInput = new TextArea();
        descriptionInput.setPromptText("Work Description \n These are bullet points so put each one on a new line.");
        descriptionInput.setPrefColumnCount(2);
        descriptionInput.setPrefRowCount(2);
        descriptionInput.setWrapText(true);

        //Button
        Button addButtonW = new Button("Add");
        addButtonW.setOnAction(e -> addButtonClickedW());
        Button deleteButtonW = new Button("Delete");
        deleteButtonW.setOnAction(e -> deleteButtonClickedW());

        HBox wBox = new HBox();
        wBox.setPadding(new Insets(10,10,10,10));
        wBox.setSpacing(10);
        wBox.getChildren().addAll(positionInputW, org2Input, startMInput,startYInput,endMInput,endYInput);

        HBox addSubBox = new HBox();
        addSubBox.setPadding(new Insets(10,10,10,10));
        addSubBox.setSpacing(10);
        addSubBox.getChildren().addAll(addButtonW, deleteButtonW);

        //tableWork.setItems(getWork());
        tableWork.getColumns().addAll(positionColumn, org2Column, startMColumn, startYColumn, endMColumn,endYColumn, descriptionColumn);

         // Label
        Label workLabel = new Label("Work Experience:");
        Label workDLabel = new Label("If End Month = Present \n Leave End Year empty");
        
        GridPane.setConstraints(workLabel, 0, 4);
        GridPane.setConstraints(workDLabel, 0, 5);
        GridPane.setConstraints(wBox, 1, 4);
        GridPane.setConstraints(descriptionInput, 1, 5);
        GridPane.setConstraints(addSubBox, 1, 6);
        GridPane.setConstraints(tableWork,1,7);

        /*
        *
        *
        *FInal Submit button
        *
        *
        */

        //Submit
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
        	makeRecord();
        	mainPage();
        });
        Button backButton = new Button("Back");
        backButton.setOnAction(e  -> {
        	/*
        	*get the information from the textField and pass it to database
        	*/
        	window.setScene(scene1);
        });
        HBox hBoxButtons = new HBox();
        hBoxButtons.setPadding(new Insets(10,10,10,10));
        hBoxButtons.setSpacing(10);
        hBoxButtons.getChildren().addAll(backButton, submitButton);
        GridPane.setConstraints(hBoxButtons, 1, 8);
        //
        grid.getChildren().addAll( expertiseLabel,hBoxExpertise, listExpertise, 
        	skillLabel, hBoxSkill, listSkills,workLabel,workDLabel,wBox, descriptionInput, addSubBox,tableWork, hBoxButtons);

        //

        scene2 = new Scene(grid, 1200, 800);
        scene2.getStylesheets().add("AddPage.css");
        return scene2;
	}
    /*
     * This method is for the deletepage of the application. This page prompts for the
     * record number you would like to remove from the database.
     *
     */
	public void deleteRecordPage(){
        Label numberLabel = new Label("What Record would you like to delete?");
        TextField numberInput = new TextField();
        numberInput.setPromptText("Insert Record Number.");

        //ADD and DELETE BUTTONS
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            int index = Integer.parseInt(numberInput.getText());
            confirmAction("You sure you would like to delete record"+ numberInput.getText());
            db.deleteRecord(index-1);
            mainPage();
        });
        Button goBackButton = new Button("Go Back");
        goBackButton.setOnAction(e -> {
            mainPage();
        });

        VBox layout = new VBox(10);
        HBox buttonBox = new HBox();
        buttonBox.setPadding(new Insets(10,10,10,10));
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(goBackButton, deleteButton);

        //Add layout
        layout.getChildren().addAll(numberLabel, numberInput, buttonBox);
        Scene sceneDelete = new Scene(layout, 1200, 800);
        window.setScene(sceneDelete);
        window.show();

	}
     /*
     * This method is for the editpage of the application. This page prompts for the
     * record number you would like to edit from the database. It grabs all the current fields 
     * for each Record, deletes the record and then goes to the adds the record back.
     *
     */
	public void editRecordPage(){

        Label numberLabel = new Label("What Record would you like to edit?");
        TextField numberInput = new TextField();
        numberInput.setPromptText("Insert Record Number.");

        //EDIT BUTTON
        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> {
            int index = Integer.parseInt(numberInput.getText());
            index -=1;
            refreshRecord(index);
            db.deleteRecord(index);
            addRecordPage();
        });

        //Add layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(numberLabel, numberInput, editButton);
        Scene sceneDelete = new Scene(layout, 1200, 800);
        window.setScene(sceneDelete);
        window.show();
	}
     /*
     * This method is for the searchpage of the application. This page prompts for the
     * key word you would like to search from the database. It searchs through all fields
     * from every record and prints out on the screen the the records that contain that keyword.
     *
     */
	public void searchRecordPage(){

		//Label
        Label searchLabel = new Label("Type the keyword you would like to search for in the database (no spaces).");

        //initailize the listView
        listSearches = new ListView<>();

        //expertise input
        TextField keyWordInput = new TextField();
        keyWordInput.setPromptText("keyword");

        //SEARCH BUTTON
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> searchRecordGUI(keyWordInput.getText()));

        //EXIT BUTTON
        Button backButton = new Button("Go Back");
        backButton.setOnAction(e -> {
        	mainPage();
        });

        //box containing buttons and text prompt
        HBox searchBox = new HBox();
        searchBox.setPadding(new Insets(10,10,10,10));
        searchBox.setSpacing(10);
        searchBox.getChildren().addAll(keyWordInput, searchButton);

        VBox layout = new VBox(10);

        //Add buttons
        layout.getChildren().addAll(searchBox, listSearches, backButton);
		Scene scene3 = new Scene(layout, 1200, 800);
        //scene2.getStylesheets().add("SearchPage.css");
        window.setScene(scene3);
        window.show();
	}
    /*
     *
     * This method is used by the addPage method when the user decides to add a new 
     * certificate
     *
     */
	public void addButtonClickedC(){
        Certificate certificate = new Certificate();
        certificate.setOrg(orgInput.getText());
        certificate.setTitle(titleInput.getText());
        tableCertificates.getItems().add(certificate);
        orgInput.clear();
        titleInput.clear();
    }
    /*
     *
     * This method is used by the addPage method when the user decides to delete a
     * certificate
     *
     */
    public void deleteButtonClickedC(){
        ObservableList<Certificate> certificatesSelected, allCertificates;
        allCertificates = tableCertificates.getItems();
        certificatesSelected = tableCertificates.getSelectionModel().getSelectedItems();

        certificatesSelected.forEach(allCertificates::remove);
    }
    // Returns the list of certificates
    public ObservableList<Certificate> getCertificates(){
        ObservableList<Certificate> certificates = FXCollections.observableArrayList();
        return certificates;
    }
    /*
     *
     * This method is used by the addPage method when the user decides to add a new 
     * work experience for the record. 
     *
     */
     //Add Button clicked
    public void addButtonClickedW(){
        Work work = new Work();
        
        work.setPosition(positionInputW.getText());
        work.setOrg(org2Input.getText());
        work.setStartMonth(startMInput.getText());
        work.setStartYear(startYInput.getText());
        work.setEndMonth(endMInput.getText());
        work.setEndYear(endYInput.getText());
        work.setDescription(descriptionInput.getText());


        tableWork.getItems().add(work);
        positionInputW.clear();
        org2Input.clear();
        startMInput.clear();
        startYInput.clear();
        endMInput.clear();
        endYInput.clear();
        descriptionInput.clear();
    }
    /*
     *
     * This method is used by the addPage method when the user decides to delete a
     * work experience.
     *
     */
    //Delete button clicked
    public void deleteButtonClickedW(){
        ObservableList<Work> worksSelected, allWork;
        allWork = tableWork.getItems();
        worksSelected = tableWork.getSelectionModel().getSelectedItems();

        worksSelected.forEach(allWork::remove);
    }
    //Get all of the work eperiences in the table
    public ObservableList<Work> getWork(){
        ObservableList<Work> works = FXCollections.observableArrayList();
        return works;
    }
    /* 
     *
     * This method allows for the user to not exit the prorgram without being promted
     * about wanting to save the database or not.
     *
     */ 
    public void closeProgram(){
    	Boolean answer = ConfirmBox.display("Exit Prompt","Do you want to Save before closing?");
    	if(answer){
    		db.saveAndExit();
    		window.close();
    	}
    	else{
    		window.close();
    	}
    }
    /* 
    *
    *This is used as a pop-up boc within the applciation when a user wants to delete a record.
    * It is an extra feature to gaurentee that the user is sure about the record they want to delete.
    *
    * Parameter - String prompt is the prompt that is displayed when the pop-up window shows up.
    *
    */
     public void confirmAction(String prompt){
        Boolean answer = ConfirmBox.display("Delete Prompt",prompt);
        if(answer){
        }
        else{
            mainPage();
        }
    }
      /* 
    *
    * This makeRecord is used in the add page when the user clicks submit. 
    * This method gets all the fields(TextFields) and inserts them into a Record 
    * temp which we will add to our database.
    *
    */
    public void makeRecord(){
        
    	    Record temp = new Record();
        	Education [] educationArray;
        	String [] expertise;
        	String [] skills;

        	temp.setName(nameInput.getText());
        	temp.setPosition(positionInput.getText());
        	Education tempE = new Education();
        	tempE.setSchool(schoolInput.getText());
        	tempE.setDegree(degreeInput.getText());
        	tempE.setMajor(majorInput.getText());
        	if (schoolInput2.getText().length() > 0){
        		educationArray = new Education[2];
        		Education tempE2 = new Education();
        		tempE2.setSchool(schoolInput2.getText());
        		tempE2.setDegree(degreeInput2.getText());
        		tempE2.setMajor(majorInput2.getText());
        		educationArray[0]=tempE;
        		educationArray[1]=tempE2;
        	}
        	else{
        		educationArray = new Education[1];
        		educationArray[0]=tempE;
        	}

        	temp.setEducation(educationArray);
        	temp.setBio(bioInput.getText());

        	//skills
        	ObservableList<Certificate> certificateList = tableCertificates.getItems();
        	int lenC = certificateList.size();
			Certificate [] certificateArray = new Certificate[lenC];
        	for(int ic=0; ic < lenC; ic++)certificateArray[ic]=certificateList.get(ic);
        	temp.setCertificate(certificateArray);

            //image 
            temp.setImage(imageInput.getText());

        	//expertise 
        	expertise=listExpertise.getItems().toArray(new String[0]);
        	temp.setExpertise(expertise);
        	skills=listSkills.getItems().toArray(new String[0]);
        	temp.setSkills(skills);
        	//Work
        	ObservableList<Work> workList= tableWork.getItems();
        	int lenW = workList.size();
			Work [] workArray = new Work[lenW];
        	for(int iw=0; iw< lenW; iw++)workArray[iw]=workList.get(iw);
        	temp.setWork(workArray);
        	ConsoleIO.printRecord( temp );
        	db.addRecord(temp);
            //clearing the inputs for next record
            nameInput.clear();
            imageInput.clear();
            positionInput.clear();
            schoolInput.clear();
            degreeInput.clear();
            majorInput.clear();
            schoolInput2.clear();
            degreeInput2.clear();
            majorInput2.clear();
            bioInput.clear();

            tableCertificates.getItems().clear();
            listExpertise.getItems().clear();
            listSkills.getItems().clear();
            tableWork.getItems().clear();

    }
    /*
     * In the search page, after clicking submit, it will call this method
     * with the keyword the user wanted to search for. This will then call 
     * a functionality of the database instance called tokenSearch which will 
     * return the records that contain that keyword as a String []. This array
     * is then diplayed in the search page.
     *
     * Parameter - String input is the keyword the user is looking for.
     *
     */
    public void searchRecordGUI(String input){
        	String [] look = db.tokenSearch(input);
            listSearches.getItems().clear();
        	if(look!=null){
        		for(String search: look) listSearches.getItems().add(search);
        	}
        	else {
        		listSearches.getItems().add("No Results Found.");
        	}
    }
    /*
     *
     *Since the edit functionality is technically deleting a record 
     * and adding the record back, we have to grab the current vlaues 
     * of the record and put it into the prompts so that the user can 
     * change them if necessary.
     * 
     * Parameter - int index is the record number that the user wants to edit.
     *
     */
    public void refreshRecord(int index){
        Record r = db.getRecord(index);
        nameInput.setText(r.getName());
        positionInput.setText(r.getPosition());
        Education [] e =r.getEducation();
        if(e.length == 2){
            schoolInput2.setText(e[1].getSchool());
            degreeInput2.setText(e[1].getDegree());
            majorInput2.setText(e[1].getMajor());
        }
        schoolInput.setText(e[0].getSchool());
        degreeInput.setText(e[0].getDegree());
        majorInput.setText(e[0].getMajor());
        bioInput.setText(r.getBio());
        tableCertificates.setItems(getCertificates());
        for(Certificate c: r.getCertificate()){
            tableCertificates.getItems().add(c);
        }
        imageInput.setText(r.getImage());
        for(String expertise: r.getExpertise()){
            listExpertise.getItems().add(expertise);
        }
        for(String skill: r.getSkills()){
            listSkills.getItems().add(skill);
        }
        tableWork.setItems(getWork());
        for(Work w: r.getWork()){
            tableWork.getItems().add(w);
        }
    }
}
