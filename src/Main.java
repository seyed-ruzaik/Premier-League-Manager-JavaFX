import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.IntStream;
import static java.util.stream.Collectors.toList;

public class Main extends Application implements Serializable {
    //premier league manager
   PremierLeagueManager plm = new PremierLeagueManager();
   //table view
   TableView <FootballClub> myTable;
   //array list which contains all played matches
   static List<String> playedMatches = new ArrayList<>();
   //array list which contains team-names
    static List<String> teamNames = new ArrayList<>();
    //Arraylist which contains max index
    static ArrayList<Integer> maxIndex = new ArrayList<>();
    //contains all randoms numbers
    static  ArrayList<String> randoms = new ArrayList<>();
    //arraylist containing dates
    static ArrayList<String> datesPlayed = new ArrayList<>();
    /*
   * if there is more than 3 teams created it will play 2 matches per day
   *
   */
    static int max = 2;
    static String startDate2 = "2020-01-01";  //Season start date
    //random integer 1
    static int random1 = 0 ;
    //random integer 2
    static int random2 = 0 ;

      //generate a random team 1
    public static int rTeam(){
        Random rand = new Random();
        return rand.nextInt(PremierLeagueManager.fClub.size());
    }
    //generate a random team 2
    public static int rTeam2(){
        Random rand2 = new Random();
        return rand2.nextInt(PremierLeagueManager.fClub.size());
    }
    /*
            evaluate the date. if the date entered is a
             previous date show an error message
    */
    public  void evaDate(Stage myStage,String date){
        Collections.sort(datesPlayed);
        if (datesPlayed.size() != 0 && !datesPlayed.get(0).equals(date) ){

                System.out.println("");
                System.out.println("-----------------------------------" +
                        "----------------------------------------");
                System.out.println("Error date cannot be a previous date " +
                        "compared to a previously played match!");
                System.out.println("-------------------------------------" +
                        "--------------------------------------");
                System.out.println("");

        }

    }
    //change the season if user enters a year which is higher than the year 2020
    public  void  changeSeason(String date){
        if (playedMatches.size() > 0 && datesPlayed.size() > 0
                && startDate2.equals("2020-01-01") && maxIndex.size() > 0){
            max = maxIndex.get(maxIndex.size()-1);
            startDate2 = datesPlayed.get(datesPlayed.size()-1);
        }
        if (compareYear(date) > compareYear(startDate2) && compareYear(startDate2) != compareYear(date)) {
          for (int ij = 0 ; ij < PremierLeagueManager.fClub.size() ; ij++){
              if (PremierLeagueManager.fClub.get(ij).getNumOfSeasons() == 0) {
                  PremierLeagueManager.fClub.get(ij).setNumOfSeasons(1);
              }else {
                  PremierLeagueManager.fClub.get(ij).setNumOfSeasons(PremierLeagueManager.
                          fClub.get(ij).getNumOfSeasons() + 1 );

              }

          }

        }

    }


   //compare the year from an entered date
    public  int compareYear(String date){



            int one = Integer.parseInt(String.valueOf(date.charAt(0)));
            int two = Integer.parseInt(String.valueOf(date.charAt(1)));
            int three = Integer.parseInt(String.valueOf(date.charAt(2)));
            int four = Integer.parseInt(String.valueOf(date.charAt(3)));


         String tog = Integer.toString(one) + Integer.toString(two) +
                 Integer.toString(three) + Integer.toString(four);



        return Integer.parseInt(tog);


    }



     //method to check if same team are playing on same day
    public int checkSameTeam(String first , String sec, String date){
        int count = 0 ;
        if (playedMatches.size() > 0 && PremierLeagueManager.fClub.size() > 0){

            for (int ij = 0 ; ij < playedMatches.size(); ij++){
                if ((playedMatches.get(ij).contains(first.toUpperCase()) ||
                        playedMatches.get(ij).contains(sec.toUpperCase())) &&
                        playedMatches.get(ij).contains(date)){
                    count++;
                }
            }

        }
        return count;
    }
     //start method
    @Override
    public void start(Stage myStage) throws Exception{
        DeSerialized();
        callMenu(myStage);


    }  //GUI Method
    @SuppressWarnings("unchecked")
    public  void table (Stage myStage){
           if (playedMatches.size() > 0 && maxIndex.size() > 0){
               max = maxIndex.get(maxIndex.size()-1);
               startDate2 = datesPlayed.get(datesPlayed.size()-1);
           }

            myStage.setTitle("Football Manager Program");
            myStage.show();

            //table club name
            TableColumn<FootballClub, String> clubNameColumn = new TableColumn<>("Club Name");
            clubNameColumn.setMinWidth(100);
            clubNameColumn.setStyle("-fx-alignment: CENTER;");
            clubNameColumn.setCellValueFactory(new PropertyValueFactory<>("clubName"));

            //table location
            TableColumn<FootballClub, String> locationColumn = new TableColumn<>("Location");
            locationColumn.setMinWidth(100);
            locationColumn.setStyle("-fx-alignment: CENTER;");
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

            //wins
            TableColumn<FootballClub, Integer> winsColumn = new TableColumn<>("Wins");
            winsColumn.setStyle("-fx-alignment: CENTER;");
            winsColumn.setMinWidth(100);
            winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));

            //draws
            TableColumn<FootballClub, Integer> drawColumn = new TableColumn<>("Draws");
            drawColumn.setMinWidth(100);
            drawColumn.setStyle("-fx-alignment: CENTER;");
            drawColumn.setCellValueFactory(new PropertyValueFactory<>("draws"));

            //defeats
            TableColumn<FootballClub, Integer> defeatColumn = new TableColumn<>("Defeats");
            defeatColumn.setMinWidth(100);
            defeatColumn.setStyle("-fx-alignment: CENTER;");
            defeatColumn.setCellValueFactory(new PropertyValueFactory<>("defeats"));

            //goals received
            TableColumn<FootballClub, Integer> grColumn = new TableColumn<>("GR");
            grColumn.setMinWidth(100);
            grColumn.setStyle("-fx-alignment: CENTER;");
            grColumn.setCellValueFactory(new PropertyValueFactory<>("numOfGoalsReceived"));

            //goals scored
            TableColumn<FootballClub, Integer> gsColumn = new TableColumn<>("GS");
            gsColumn.setMinWidth(100);
            gsColumn.setStyle("-fx-alignment: CENTER;");
            gsColumn.setCellValueFactory(new PropertyValueFactory<>("numOfGoalsScored"));

            //points
            TableColumn<FootballClub, Integer> pointsColumn = new TableColumn<>("Points");
            pointsColumn.setMinWidth(100);
            pointsColumn.setStyle("-fx-alignment: CENTER;");
            pointsColumn.setCellValueFactory(new PropertyValueFactory<>("numOfPoints"));

            //matches-played
            TableColumn<FootballClub, Integer> matchColumn = new TableColumn<>("Matches-Played");
            matchColumn.setMinWidth(100);
            matchColumn.setStyle("-fx-alignment: CENTER;");
            matchColumn.setCellValueFactory(new PropertyValueFactory<>("numOfMatches"));

            //total seasons played
            TableColumn<FootballClub, Integer> seasonsColumn = new TableColumn<>("Seasons-Played");
            seasonsColumn.setMinWidth(100);
            seasonsColumn.setStyle("-fx-alignment: CENTER;");
            seasonsColumn.setCellValueFactory(new PropertyValueFactory<>("numOfSeasons"));

            //to creating the table using table view
        myTable = new TableView<>(); //By using table view i have created the table.
        myTable.getColumns().addAll(clubNameColumn, locationColumn, winsColumn, drawColumn,
                    defeatColumn, grColumn, gsColumn,
                    pointsColumn, matchColumn, seasonsColumn);
        myTable.setItems(footballClub());
        //remove unwanted columns
        myTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            VBox vBox = new VBox();
            Button menu = new Button("Menu");
            Button wins = new Button("Sort-Wins");
            Button gs = new Button("Sort-GS");
            Button points = new Button("Sort-Points");
            Button playedMatch = new Button("Generate a match");
            Button sortPlayedMatches = new Button("Show Played\nMatches");
            Button search = new Button("Go to\nSearch");
            Label update = new Label();

            //set the layouts for the buttons, text fields and labels
            menu.setTranslateX(520.0);
            menu.setTranslateY(55.5);

            wins.setTranslateX(185.0);
            wins.setTranslateY(29.0);

            gs.setTranslateX(110.0);
            gs.setTranslateY(2.0);

            points.setTranslateX(15.0);
            points.setTranslateY(-25.0);

            playedMatch.setTranslateX(275.0);
            playedMatch.setTranslateY(-52.0);

            update.setTranslateX(475.0);
            update.setTranslateY(24.0);

            sortPlayedMatches.setTranslateX(15.0);
            sortPlayedMatches.setTranslateY(-52.0);

            search.setTranslateX(145.0);
            search.setTranslateY(-92.5);

            //set the label visibility as false
            update.setVisible(false);
            vBox.getChildren().addAll(myTable, menu, wins, gs, points, playedMatch, update,
                    sortPlayedMatches, search);
            Scene scene = new Scene(vBox);
            myStage.setScene(scene);
            //external stylesheet for the GUI
            scene.getStylesheets().add("Table.css");
            //set the function for menu button
            menu.setOnAction(event -> {
                update.setVisible(false);
                myStage.close();
                callMenu(myStage);
            });   //set function for win button
            wins.setOnAction(event -> {
                myTable.setItems(footballClub3());

            }); //set the function for gs button
            gs.setOnAction(event -> {
                myTable.setItems(footballClub2());
            }); //set the function for points button
            points.setOnAction(event -> {
                myTable.setItems(footballClub());
            }); //set disable the auto generate a played match button when user has created less than two teams
        playedMatch.setDisable(PremierLeagueManager.fClub.size() < 2);
        search.setDisable(playedMatches.size() == 0);
        sortPlayedMatches.setDisable(playedMatches.size() == 0);
            playedMatch.setOnAction(event -> {
                update.setStyle(" -fx-border-color: white;-fx-text-fill: brown; " +
                        "-fx-background-color:yellow;-fx-font-size: 14");
                update.setVisible(true);

                //random scores for both teams
                Random rand3 = new Random();
                Random rand4 = new Random();

                int score1 = rand3.nextInt(20);
                int score2 = rand4.nextInt(20);

                 //add all the teams in fClub to teamNames array list
             for (int ij = 0; ij < PremierLeagueManager.fClub.size();ij++){
                 if (!teamNames.contains(PremierLeagueManager.fClub.get(ij).getClubName())) {
                     teamNames.add(PremierLeagueManager.fClub.get(ij).getClubName());
                 }
             }



               //if there is only 2 or 3 teams this method will generate a match on a each new day
               if (PremierLeagueManager.fClub.size() == 2 || PremierLeagueManager.fClub.size() == 3) {

                   List<Integer> myList = IntStream.range(0,
                           PremierLeagueManager.fClub.size()).boxed().collect(toList());
                   Collections.shuffle(myList);
                   List<Integer> myList2 = myList.subList(0, 2);

                   SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
                   Calendar cal = Calendar.getInstance();
                   try {
                       cal.setTime(dateF.parse(startDate2));
                       if (playedMatches.size() > 0) {
                           cal.add(Calendar.DATE, 1);
                       }else {
                           cal.add(Calendar.DATE, 0);

                       }
                       startDate2 = dateF.format(cal.getTime());
                   } catch (ParseException e) {
                       e.printStackTrace();
                   }
                   datesPlayed.add(startDate2);
                   update.setText("|" +
                           PremierLeagueManager.fClub.get(myList2.get(0)).getClubName().toUpperCase()
                           + "  " + score1 + " : "
                           + score2 + "  " +
                           PremierLeagueManager.fClub.get(myList2.get(1)).getClubName().toUpperCase()
                           + "|" + "\n|Date Played: " +
                           startDate2 + "|");
                   plm.addPlayedMatch(PremierLeagueManager.fClub.get(myList2.get(0)).getClubName(), score1,
                           PremierLeagueManager.fClub.get(myList2.get(1)).getClubName(),
                           score2, startDate2);
                   playedMatches.add("|Date Played: " +
                           startDate2 + "|" + "\n" + "|" +
                           PremierLeagueManager.fClub.get(myList2.get(0)).getClubName().toUpperCase()
                           + "  " + score1 + " : "
                           + score2 + "  "
                           + PremierLeagueManager.fClub.get(myList2.get(1)).getClubName().toUpperCase()
                           + "|");
                   myTable.setItems(footballClub());

               }else {

                   //random clubs
                   if(randoms.size() > 1 && random1 == 0 && random2 == 0){
                       random1 += teamNames.indexOf(randoms.get(randoms.size()-1));
                       random2 += teamNames.indexOf(randoms.get(randoms.size()-2));
                   }
                   //if a club is deleted after a match is played on a particular date
                   // making sure it won't generate the same team again
                   if (randoms.size() > 1 && playedMatches.size()  > 0){
                       if (!teamNames.contains(randoms.get(randoms.size()-1)) ||
                               !teamNames.contains(randoms.get(randoms.size()-2))){
                          String club1  = randoms.get(randoms.size()-1);
                          String club2  = randoms.get(randoms.size()-2);
                           System.out.println("It came here");

                          if (teamNames.contains(club1)){
                              random1 = 0 ;
                              random1 += teamNames.indexOf(club1);
                              System.out.println("Came Here A");

                          }else if (teamNames.contains(club2)){
                              random1 = 0 ;
                              random1 += teamNames.indexOf(club2);
                              System.out.println("Came Here B");
                          }
                       }
                   }


                        //if there is more than 3 teams created by the user 2 matches will be played on a day

                   SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
                   Calendar cal = Calendar.getInstance();
                   if ((max == 0)){
                       random1 = 0 ;
                       random2 = 0 ;
                       max += 2;
                       try {
                           cal.setTime(dateF.parse(startDate2));
                           cal.add(Calendar.DATE, 1);
                           startDate2 = dateF.format(cal.getTime());

                       } catch (ParseException e) {
                           e.printStackTrace();
                       }
                   }


                 int temp1 = 0 ;
                   int temp2 = 0;

                   temp1 += random1;
                   temp2 += random2;

                   int rand1 = rTeam();
                   int rand2 = rTeam2();

                   while (true) {
                       if ((rand1 != rand2 && (random1 != rand1) && (random2 != rand2))) {
                           random1 = rand1;
                           random2 = rand2;

                           if (random1 != temp2 && random2 != temp1 && random1 != temp1
                                   && random2 != temp2 ) {
                               temp1 = 0;
                               temp2 = 0 ;
                               break;
                           }

                       } else {
                           rand1 = rTeam();
                           rand2 = rTeam2();
                       }
                   }


                   if (max > 0){
                       //sort them according to GD
                       PremierLeagueManager.fClub.sort(new Comparator<FootballClub>() {

                           public int compare(FootballClub footballClub, FootballClub footballClub2) {
                               return footballClub.getDiff() - footballClub2.getDiff();
                           }
                       });  //sort them according to points
                       PremierLeagueManager.fClub.sort(new Comparator<FootballClub>() {

                           public int compare(FootballClub footballClub3, FootballClub footballClub4) {
                               return footballClub3.getNumOfPoints() - footballClub4.getNumOfPoints();
                           }
                       });
                       Collections.reverse(PremierLeagueManager.fClub);
                       datesPlayed.add(startDate2);
                       update.setText("|" + teamNames.get(random1).toUpperCase()
                               + "  " + score1 + " : "
                               + score2 + "  " + teamNames.get(random2).toUpperCase()
                               + "|" + "\n|Date Played: " +
                               startDate2 + "|");
                       plm.addPlayedMatch(teamNames.get(random1), score1,
                               teamNames.get(random2),
                               score2, startDate2);
                       playedMatches.add("|Date Played: " +
                               startDate2 + "|" + "\n" + "|" +
                              teamNames.get(random1).toUpperCase()
                               + "  " + score1 + " : "
                               + score2 + "  "
                               + teamNames.get(random2).toUpperCase() + "|");
                       randoms.add(teamNames.get(random1));
                       randoms.add(teamNames.get(random2));





                       myTable.setItems(footballClub());
                       if (playedMatches.size() >= 2){

                               maxIndex.clear();

                       }



                    max--;



                   }


                   maxIndex.add(max);


               }
                search.setDisable(playedMatches.size() == 0);
                sortPlayedMatches.setDisable(playedMatches.size() == 0);

            });  //set function to sort played matches button
            sortPlayedMatches.setOnAction(event -> {
                Button goBack = new Button("Go-Back");
                goBack.setVisible(true);
                myStage.close();
                AnchorPane pane = new AnchorPane();
                Scene scene2 = new Scene(pane, 900, 650);
                myStage.setScene(scene2);

                //set layouts for the buttons
                goBack.setTranslateX(300.0);
                goBack.setTranslateY(454.6);

                menu.setTranslateX(559.5);
                menu.setTranslateY(59.5);

                pane.getChildren().addAll(menu, goBack);
                scene2.getStylesheets().add("Table.css");
                ListView<String> listOfMatches = new ListView<>();
                listOfMatches.setPrefHeight(350);
                listOfMatches.setPrefWidth(800);
                listOfMatches.setTranslateX(50);
                listOfMatches.setTranslateY(50);
                listOfMatches.setStyle(" -fx-text-fill: black;-fx-font-weight: bold;");
                Collections.sort(playedMatches);
                ObservableList<String> allMatches = FXCollections.observableArrayList();
                allMatches.addAll(playedMatches);
                listOfMatches.getItems().addAll(allMatches);
                pane.getChildren().add(listOfMatches);
                myStage.show();
                goBack.setOnAction(event1 -> {
                    myStage.close();
                    table(myStage);

                });

            }); //search button set the function for it
            search.setOnAction(event -> {
                Button goBack = new Button("Go-Back");
                Button searchItem = new Button("Go");
                TextField searchBar = new TextField();
                searchBar.setStyle(" -fx-border-color:brown;");
                goBack.setVisible(true);
                myStage.close();
                AnchorPane pane2 = new AnchorPane();
                Scene scene3 = new Scene(pane2, 650, 600);
                myStage.setScene(scene3);

                goBack.setTranslateX(300.0);
                goBack.setTranslateY(454.6);

                menu.setTranslateX(559.5);
                menu.setTranslateY(59.5);

                searchBar.setTranslateX(54.5);
                searchBar.setTranslateY(20.0);
                searchBar.setMinWidth(150.0);
                searchItem.setTranslateX(240.5);
                searchItem.setTranslateY(20.0);

                searchBar.setPromptText("YYYY-MM-DD");

                pane2.getChildren().addAll(menu, goBack, searchItem, searchBar);
                scene3.getStylesheets().add("Table.css");

                ListView<String> listOfPlayedMatches = new ListView<>();
                listOfPlayedMatches.setPrefHeight(250);
                listOfPlayedMatches.setPrefWidth(310);
                listOfPlayedMatches.setTranslateX(50.0);
                listOfPlayedMatches.setTranslateY(80.0);
                listOfPlayedMatches.setStyle(" -fx-text-fill: black;-fx-font-weight: bold;");
                pane2.getChildren().add(listOfPlayedMatches);
                ObservableList<String> showMatches = FXCollections.observableArrayList();

                searchItem.setOnAction(event1 -> {

                    SimpleDateFormat playedDate = new SimpleDateFormat("yyyy-MM-dd");
                    Date searchDate = null;
                    try {
                        //Parsing the String
                        playedDate.setLenient(false);
                        searchDate = playedDate.parse(searchBar.getText());


                    } catch (DateTimeParseException | ParseException ex) {


                    }
                    showMatches.clear();
                    listOfPlayedMatches.getItems().clear();
                    for (int ij = 0; ij < playedMatches.size(); ij++) {
                        if (playedMatches.get(ij).contains(searchBar.getText()) && searchDate != null) {
                            showMatches.add(playedMatches.get(ij));
                            listOfPlayedMatches.getItems().add(playedMatches.get(ij));


                        }
                    }


                });
                goBack.setOnAction(event1 -> {
                    myStage.close();
                    table(myStage);
                });
                myStage.show();


            });

    }


          //observer list 1 for sorting the teams according to points
    public ObservableList<FootballClub> footballClub (){
        ObservableList<FootballClub> clubs = FXCollections.observableArrayList();
        //sort them according to GD
        PremierLeagueManager.fClub.sort(new Comparator<FootballClub>() {

            public int compare(FootballClub footballClub, FootballClub footballClub2) {
                return footballClub.getDiff() - footballClub2.getDiff();
            }
        });  //sort them according to points
        PremierLeagueManager.fClub.sort(new Comparator<FootballClub>() {

            public int compare(FootballClub footballClub3, FootballClub footballClub4) {
                return footballClub3.getNumOfPoints() - footballClub4.getNumOfPoints();
            }
        }); // by using the for loop to adding them to the List
        for(int ij = PremierLeagueManager.fClub.size(); ij > 0; ij--){

            clubs.add(new FootballClub(
                    PremierLeagueManager.fClub.get(ij-1).getClubName()
                    ,PremierLeagueManager.fClub.get(ij-1).getLocation(),
                    PremierLeagueManager.fClub.get(ij-1).getWins(),
                    PremierLeagueManager.fClub.get(ij-1).getDraws(),
                    PremierLeagueManager.fClub.get(ij-1).getDefeats(),
                    PremierLeagueManager.fClub.get(ij-1).getNumOfGoalsReceived(),
                    PremierLeagueManager.fClub.get(ij-1).getNumOfGoalsScored(),
                    PremierLeagueManager.fClub.get(ij-1).getNumOfPoints(),
                    PremierLeagueManager.fClub.get(ij-1).getNumOfMatches(),
                    PremierLeagueManager.fClub.get(ij-1).getNumOfSeasons())) ;

        }


        return clubs;
    }
    //observerList 2 for sorting the teams according to GS
    public ObservableList<FootballClub> footballClub2 (){
        ObservableList<FootballClub> clubs2 = FXCollections.observableArrayList();
        //sort them according to GS
        PremierLeagueManager.fClub.sort(new Comparator<FootballClub>() {

            public int compare(FootballClub footballClub, FootballClub footballClub2) {
                return footballClub.getNumOfGoalsScored() - footballClub2.getNumOfGoalsScored();
            }
        });
          // by using the for loop to adding them to the List
        for(int ij = PremierLeagueManager.fClub.size(); ij > 0; ij--){

            clubs2.add(new FootballClub(
                    PremierLeagueManager.fClub.get(ij-1).getClubName()
                    ,PremierLeagueManager.fClub.get(ij-1).getLocation(),
                    PremierLeagueManager.fClub.get(ij-1).getWins(),
                    PremierLeagueManager.fClub.get(ij-1).getDraws(),
                    PremierLeagueManager.fClub.get(ij-1).getDefeats(),
                    PremierLeagueManager.fClub.get(ij-1).getNumOfGoalsReceived(),
                    PremierLeagueManager.fClub.get(ij-1).getNumOfGoalsScored(),
                    PremierLeagueManager.fClub.get(ij-1).getNumOfPoints(),
                    PremierLeagueManager.fClub.get(ij-1).getNumOfMatches(),
                    PremierLeagueManager.fClub.get(ij-1).getNumOfSeasons()));

        }


        return clubs2;
    }
       //observerList 3 for sorting the team according to wins(Largest)
    public ObservableList<FootballClub> footballClub3 (){
        ObservableList<FootballClub> clubs3 = FXCollections.observableArrayList();
        //sort them according to wins
        PremierLeagueManager.fClub.sort(new Comparator<FootballClub>() {

            public int compare(FootballClub footballClub, FootballClub footballClub2) {
                return footballClub.getWins() - footballClub2.getWins();
            }
        });
        // by using the for loop to adding them to the List
        for(int ij = PremierLeagueManager.fClub.size(); ij > 0; ij--){

            clubs3.add(new FootballClub(
                    PremierLeagueManager.fClub.get(ij-1).getClubName()
                    ,PremierLeagueManager.fClub.get(ij-1).getLocation(),
                    PremierLeagueManager.fClub.get(ij-1).getWins(),
                    PremierLeagueManager.fClub.get(ij-1).getDraws(),
                    PremierLeagueManager.fClub.get(ij-1).getDefeats(),
                    PremierLeagueManager.fClub.get(ij-1).getNumOfGoalsReceived(),
                    PremierLeagueManager.fClub.get(ij-1).getNumOfGoalsScored(),
                    PremierLeagueManager.fClub.get(ij-1).getNumOfPoints(),
                    PremierLeagueManager.fClub.get(ij-1).getNumOfMatches(),
                    PremierLeagueManager.fClub.get(ij-1).getNumOfSeasons()));

        }


        return clubs3;
    }


    //display the menu by callMenu Method
    public void callMenu(Stage myStage) {
        System.out.println("");
        System.out.println("");
        System.out.println("                _______________________________________" +
                "_______________________________________");
        System.out.println("                                                            " +
                "                 ");
        System.out.println("                                      WELCOME TO FOOTBALL " +
                "MANAGER PROGRAM!                      ");
        System.out.println("                ____________________________________________" +
                "__________________________________");
        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("a) Enter 1 to - Add a new club");
        System.out.println("b) Enter 2 to - Show statistics for a club");
        System.out.println("c) Enter 3 to - Display league table");
        System.out.println("d) Enter 4 to - Delete a club");
        System.out.println("e) Enter 5 to - Add a played match and update it's scores");
        System.out.println("f) Enter 6 to - Open gui menu");
        System.out.println("g) Enter 7 to - To save and exit the program");
        //switch case for calling the appropriate methods for the appropriate options selected by the user.
        System.out.println("");
        System.out.print("Enter an option : ");

        if (playedMatches.size() > 0 && PremierLeagueManager.fClub.size() == 0){
            playedMatches.clear();
            datesPlayed.clear();
            maxIndex.clear();
            teamNames.clear();
            max = 2;
            startDate2 = "2020-01-01";
            PremierLeagueManager.exist.clear();
            PremierLeagueManager.names.clear();
            PremierLeagueManager.played.clear();
            randoms.clear();
        }

        String option = scanner.nextLine(); //select any option
        if (option.isEmpty()){
            System.out.println("");
            System.out.println("----------------------------------");
            System.out.println("Invalid Input! Please input again!");
            System.out.println("----------------------------------");
            System.out.println("");
            callMenu(myStage);
        }else {
            switch (option) {
                case "1":
                    if (playedMatches.size() == 0) {
                        addFootballClub(myStage);
                    }else {
                        System.out.println("");
                        System.out.println("----------------------------------" +
                                "------------------------------------");
                        System.out.println("League already started. " +
                                "You cannot create any new clubs at the moment!");
                        System.out.println("---------------------------------------" +
                                "-------------------------------");
                        System.out.println("");
                        callMenu(myStage);

                    }
                    break;
                case "2":
                    showClubStats(myStage);
                    callMenu(myStage);
                    break;
                case "3":
                    showThePremierLeague(myStage);
                    callMenu(myStage);
                    break;
                case "4":
                    delete();
                    callMenu(myStage);
                    break;
                case "5":
                    matchScoreUpdate(myStage);
                    callMenu(myStage);

                    break;
                case "6":
                    if (PremierLeagueManager.fClub.size() == 0){
                        System.out.println("");
                        System.out.println("--------------------------------");
                        System.out.println("Add A Club First Then Try Again!");
                        System.out.println("--------------------------------");
                        System.out.println("");
                        callMenu(myStage);
                    }else {
                        table(myStage);
                    }
                    break;

                case "7":
                    serialize();
                    System.out.println("");
                    System.out.println("____________________________");
                    System.out.println("you have quited the program!");
                    System.out.println("____________________________");
                    System.exit(0);
                    break;
                //if user enters a invalid option display an error message to the user
                default:
                    System.out.println("");
                    System.out.println("----------------------------------");
                    System.out.println("Invalid input! Please input again!");
                    System.out.println("----------------------------------");
                    System.out.println("");
                    callMenu(myStage);
            }
        }
    }

    //add a new football club only if it's not existing already
    public void addFootballClub(Stage myStage) throws InputMismatchException {
        myStage.close();
        Scanner sc1 = new Scanner(System.in);
        FootballClub footballClub = new FootballClub("", "", 0, 0, 0,
                0, 0, 0, 0, 0);

        //add the club names in fClub arraylist to exist arraylist if it doesn't contain the names
        for(int ij = 0 ; ij<PremierLeagueManager.fClub.size();ij++){
            if(!PremierLeagueManager.exist.contains(PremierLeagueManager.fClub.get(ij).getClubName())) {
                PremierLeagueManager.exist.add(PremierLeagueManager.fClub.get(ij).getClubName());
            }
        }
          //display a message when user selects the option 1
        System.out.println("");
        System.out.println("	 ______________________________________________________________");
        System.out.println("	                       Add-Football-Club               ");
        System.out.println("	 ______________________________________________________________");
        System.out.println("");
        System.out.print("a) What is the name of the club : ");
        String name = (sc1.nextLine().toLowerCase());
        if(name.isEmpty()){  //check if the input is empty or not if it is show an error message
            System.out.println("");
            System.out.println("----------------------------------");
            System.out.println("Invalid Input! Please enter again!");
            System.out.println("----------------------------------");

        }else {
            if (PremierLeagueManager.exist.contains(name)) { //if club exists already show an error message
                System.out.println("");
                System.out.println("-------------------");
                System.out.println("Club Already Exist!");
                System.out.println("-------------------");
            } else {
                System.out.print("b) What is the location of the club :");
                String location = (sc1.nextLine().toLowerCase());
                if (location.isEmpty()){ //check if the input is empty or not if it is show an error message
                    System.out.println("");
                    System.out.println("----------------------------------");
                    System.out.println("Invalid Input! Please enter again!");
                    System.out.println("----------------------------------");
                }else {

                    //add the information

                    PremierLeagueManager premierLeagueManager = new PremierLeagueManager();


                               //add
                            premierLeagueManager.addNewClub(footballClub, name, location, 0, 0,
                                    0,
                                    0, 0, 0,
                                    0, 0);
                            System.out.println("");
                            System.out.println("-----------");
                            System.out.println("Club Added!");
                            System.out.println("-----------"); //show a message when club is successfully added.


                 }
               }
        }
        System.out.println("");
        callMenu(myStage); // call the menu when all is done
    }



    //show stats for a club
    public void showClubStats(Stage myStage) {
        myStage.close();

        if (PremierLeagueManager.fClub.size() == 0 ) {
            System.out.println("");
            System.out.println("-----------------------------------------------------------------");
            System.out.println("No Clubs available at the moment try adding a club and try again!");
            System.out.println("-----------------------------------------------------------------");
            System.out.println("");


        } else {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter name of the Club: ");
            String name = sc.nextLine().toLowerCase();
            plm.showClubStats(name);



        }

    }
    //show the premiere League table
    public void showThePremierLeague(Stage myStage) {
        if (PremierLeagueManager.fClub.size() == 0 ) {
            System.out.println("");
            System.out.println("-------------------------------------");
            System.out.println("Error-Add a club first and try again!");
            System.out.println("_____________________________________");
            System.out.println("");


        } else {
            System.out.println("");
            plm.displayLeagueTable(); //display the table

        }
    }
    // delete a club method
    public void delete() {
        if (PremierLeagueManager.fClub.size()==0){ //show an error message when no clubs are created
            System.out.println("");
            System.out.println("-----------------------------");
            System.out.println("No Clubs Available To Delete!");
            System.out.println("-----------------------------");
            System.out.println("");
        }else {

            Scanner delete = new Scanner(System.in);
            System.out.print("Enter the name of the club:");
            String name = delete.nextLine().toLowerCase();
            plm.deleteClub(name);
        }

    }
    //save all data to a file method
    public void serialize() {


        plm.serializeClub();

        try {
            //save all the information in a .ser file
            FileOutputStream stream = new FileOutputStream("Played-Matches.ser");
            ObjectOutputStream objOut = new ObjectOutputStream(stream);

            objOut.writeObject(playedMatches);

            objOut.close();
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            //save all the information in a .ser file
            FileOutputStream stream = new FileOutputStream("DatesPlayed.ser");
            ObjectOutputStream objOut = new ObjectOutputStream(stream);

            objOut.writeObject(datesPlayed);

            objOut.close();
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            //save all the information in a .ser file
            FileOutputStream stream = new FileOutputStream("MaxIndex.ser");
            ObjectOutputStream objOut = new ObjectOutputStream(stream);

            objOut.writeObject(maxIndex);

            objOut.close();
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            //save all the information in a .ser file
            FileOutputStream stream = new FileOutputStream("RandomClubs.ser");
            ObjectOutputStream objOut = new ObjectOutputStream(stream);

            objOut.writeObject(randoms);

            objOut.close();
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //load all the data to data structure method
    @SuppressWarnings("unchecked")
    public void DeSerialized() throws IOException, ClassNotFoundException {
        plm.deserializeClub();

        try {
            ArrayList<String> deSerialized = new ArrayList<String>();
            //from the file Played-Matches.ser load the all the data to the data structure
            FileInputStream myFile = new FileInputStream("Played-Matches.ser");
            ObjectInputStream input = new ObjectInputStream(myFile);


            deSerialized = (ArrayList<String>) input.readObject();

            playedMatches.addAll(deSerialized);

        }catch (FileNotFoundException ignored){}

        try {
            ArrayList<String> deSerialized = new ArrayList<String>();
            //from the file Played-Matches.ser load the all the data to the data structure
            FileInputStream myFile = new FileInputStream("DatesPlayed.ser");
            ObjectInputStream input = new ObjectInputStream(myFile);


            deSerialized = (ArrayList<String>) input.readObject();

            datesPlayed.addAll(deSerialized);

        }catch (FileNotFoundException ignored){}



        try {
            ArrayList<Integer> deSerialized = new ArrayList<Integer>();
            //from the file Played-Matches.ser load the all the data to the data structure
            FileInputStream myFile = new FileInputStream("MaxIndex.ser");
            ObjectInputStream input = new ObjectInputStream(myFile);


            deSerialized = (ArrayList<Integer>) input.readObject();

            maxIndex.addAll(deSerialized);

        }catch (FileNotFoundException ignored){}

        try {
            ArrayList<String> deSerialized = new ArrayList<String>();
            //from the file Played-Matches.ser load the all the data to the data structure
            FileInputStream myFile = new FileInputStream("RandomClubs.ser");
            ObjectInputStream input = new ObjectInputStream(myFile);


            deSerialized = (ArrayList<String>) input.readObject();

            randoms.addAll(deSerialized);

        }catch (FileNotFoundException ignored){}



    }





    //add a played match between 2 teams and update it's score
    public void matchScoreUpdate(Stage myStage) throws NumberFormatException{
        int year = 2020;
       //if there is less than 2 clubs created user cannot update the score hence show an error message
        if (PremierLeagueManager.fClub.size() == 0 || PremierLeagueManager.fClub.size() < 2){
            System.out.println("");
            System.out.println("--------------------------------------");
            System.out.println("Add At Least Two Clubs Then Try Again!");
            System.out.println("--------------------------------------");
            System.out.println("");

            //(if there is only 2 or 3 teams)
        }else if (PremierLeagueManager.fClub.size() <= 3 ){
            int count = 0;
            int count2 = 0;

            //setup the scanner
            Scanner sc = new Scanner(System.in);
            System.out.print("i) Enter the played match date in (yyyy-mm-dd) format: ");
            String datePlayed = sc.nextLine();
            SimpleDateFormat matchDate = new SimpleDateFormat("yyyy-MM-dd"); //date
            Date resultDate = null;
            try {
                //Parsing the String
                matchDate.setLenient(false);
                resultDate = matchDate.parse(datePlayed);


            } catch (DateTimeParseException | ParseException ex) {
                System.out.println("");

            }


            if (datePlayed.isEmpty()) {
                System.out.println("");
                System.out.println("--------------");
                System.out.println("Invalid Input!");
                System.out.println("--------------");
                System.out.println("");
            }else if (datePlayed.length() < 10 || resultDate == null){ //error message when date is invalid
                System.out.println("");
                System.out.println("-----------------------------------------");
                System.out.println("|Date Error| - Please enter a valid date!");
                System.out.println("-----------------------------------------");
                System.out.println("");
            }




                else {

                try { //try and catch
                    SimpleDateFormat check = new SimpleDateFormat("yyyy-MM-dd");

                    // comparing both of these dates
                    Date date1 = check.parse(datePlayed);
                    if(datesPlayed.size() > 0) {
                        for (int ij = 0; ij < datesPlayed.size(); ij++) {
                            if (datesPlayed.get(ij).equals(datePlayed)) {
                                count += 1;
                            }
                        }
                        Date date2 = check.parse(datesPlayed.get(datesPlayed.size() - 1));
                        if (date1.before(date2)) {
                            count2 += 1;
                        }
                    }

                }catch (ParseException exception){
                    exception.printStackTrace();
                }

                if (count > 0 ) { //show an error message when user enters a date which a match already played.


                        System.out.println("");
                        System.out.println("-----------------------------------------" +
                                "-----------------------------");
                        System.out.println("Enter a different date please. A match is " +
                                "already played on that date!");
                        System.out.println("-------------------------------------------" +
                                "---------------------------");
                        System.out.println("");


                }


                else if (compareYear(datePlayed) < year) { //if user enters a year which is less than 2020

                    System.out.println("");
                    System.out.println("----------------------------------------------------");
                    System.out.println("Enter a year which is greater than or equal to 2020!");
                    System.out.println("----------------------------------------------------");
                    System.out.println("");

                } else if (count2 > 0) {

                    evaDate(myStage, datePlayed);
                }

                else {
                    System.out.print("ii) Enter the name the first club:");
                    String firstClub = sc.nextLine().toLowerCase();
                    System.out.print("iii) Enter the name of second club:");
                    String secondClub = sc.nextLine().toLowerCase();
                    if (firstClub.isEmpty() || secondClub.isEmpty()){//if user enters nothing
                        System.out.println("");
                        System.out.println("--------------");
                        System.out.println("Invalid Input!");
                        System.out.println("--------------");
                        System.out.println("");
                    } else {
                        for (int ij = 0; ij < PremierLeagueManager.fClub.size();ij++){
                            if (!teamNames.contains(PremierLeagueManager.fClub.get(ij).getClubName())) {
                                teamNames.add(PremierLeagueManager.fClub.get(ij).getClubName());
                            }
                        }
                        if (!firstClub.equals(secondClub)) {
                            if (teamNames.contains(firstClub) && teamNames.contains(secondClub)) {
                                try {
                                    System.out.print("iv) Enter the score of first club:"); //club 1
                                    int score1 = sc.nextInt();
                                    System.out.print("v) Enter the score of second club:");//club 2
                                    int score2 = sc.nextInt();
                                    if (score1 >= 0 && score2 >= 0) {
                                        plm.addPlayedMatch(firstClub, score1, secondClub, score2, datePlayed);
                                        if (PremierLeagueManager.played.contains(score1) &&
                                                PremierLeagueManager.played.contains(score2)) {
                                            System.out.println("");
                                            System.out.println("___________________");
                                            System.out.println("Scores are updated!"); //show success message
                                            System.out.println("___________________");
                                            System.out.println("");
                                            playedMatches.add("|Date Played: " +
                                                    datePlayed + "|" + "\n" + "|" + firstClub.toUpperCase()
                                                    + "  " + score1 + " : " + score2 + "  " +
                                                    secondClub.toUpperCase() + "|");
                                            datesPlayed.add(datePlayed);
                                            changeSeason(datePlayed);
                                            if (max > 0) {
                                                startDate2 = "";
                                                startDate2 += datePlayed;
                                                max--;
                                                maxIndex.add(max);
                                            } else if (max == 0) {
                                                max = 1;
                                                maxIndex.add(max);


                                            }
                                            PremierLeagueManager.played.clear();
                                        } else {
                                            System.out.println();
                                            //nothing
                                        }
                                    } else { //if user enters a negative score show an error message
                                        System.out.println("");
                                        System.out.println("------------------------------------" +
                                                "-------------------------------");
                                        System.out.println("Cant' update the scores because you " +
                                                "have entered a negative score !");
                                        System.out.println("--------------------------------------" +
                                                "-----------------------------");
                                        System.out.println("");
                                    }
                                } catch (InputMismatchException ex) { //if user enters a string show an error message.
                                    System.out.println("");
                                    System.out.println("-----------------");
                                    System.out.println("Enter an Integer!");
                                    System.out.println("-----------------");
                                    System.out.println("");

                                }
                            } else {
                                System.out.println("");
                                System.out.println("----------------------------------------------------");
                                System.out.println("|Error| - Check the clubs you entered and try again!");
                                System.out.println("----------------------------------------------------");
                                System.out.println("");
                            }
                        } else { //show an error message if both teams are by user are the same
                            System.out.println("");
                            System.out.println("----------------------------------------------" +
                                    "------------------------");
                            System.out.println("Error cannot update scores if first club and " +
                                    "second club are the same!");
                            System.out.println("---------------------------------------------" +
                                    "-------------------------");
                            System.out.println("");
                        }
                    }
                }
            }
            // go to this condition if more than 3 teams created.
        }else {
            int count = 0;
            int count2 = 0;
            Scanner sc = new Scanner(System.in);
            System.out.print("i) Enter the played match date in (yyyy-mm-dd) format: ");
            String datePlayed = sc.nextLine();
            SimpleDateFormat matchDate = new SimpleDateFormat("yyyy-MM-dd");
            Date resultDate = null;
            try {
                //Parsing the String
                matchDate.setLenient(false);
                resultDate = matchDate.parse(datePlayed);


            } catch (DateTimeParseException | ParseException ex) {
                System.out.println("");

            }

            if (datePlayed.isEmpty()) {
                System.out.println("");
                System.out.println("--------------");
                System.out.println("Invalid Input!"); //if user enters nothing show a message
                System.out.println("--------------");
                System.out.println("");

            } //if date is invalid display an error message
            else if (datePlayed.length() < 10 || resultDate == null){
                System.out.println("");
                System.out.println("-----------------------------------------");
                System.out.println("|Date Error| - Please enter a valid date!");
                System.out.println("-----------------------------------------");
                System.out.println("");
            }
            else {

                try { //try and catch
                    SimpleDateFormat check = new SimpleDateFormat("yyyy-MM-dd");

                    // comparing both of these dates
                    Date date1 = check.parse(datePlayed);
                    if(datesPlayed.size() > 0) {
                        for (int ij = 0; ij < datesPlayed.size(); ij++) {
                            if (datesPlayed.get(ij).equals(datePlayed)) {
                                count += 1;
                            }
                        }
                        Date date2 = check.parse(datesPlayed.get(datesPlayed.size() - 1));
                        if (date1.before(date2)) {
                            count2 += 1;
                        }
                    }

                }catch (ParseException exception){
                    exception.printStackTrace();
                }
                       //if count is equal 2 that means 2 matches already played on that
                  if (count == 2 ) {

                    System.out.println("");
                    System.out.println("-----------------------------------------------------------------------");
                    System.out.println("Enter a different date please. Two matches already played on that date!");
                    System.out.println("-----------------------------------------------------------------------");
                    System.out.println("");

                } else if (compareYear(datePlayed) < year) { //show an error message for year which less than 2020.

                    System.out.println("");
                    System.out.println("----------------------------------------------------");
                    System.out.println("Enter a year which is greater than or equal to 2020!");
                    System.out.println("----------------------------------------------------");
                    System.out.println("");

                } else if (count2 > 0) {

                    evaDate(myStage, datePlayed);
                }
                  else {
                      for (int ij = 0; ij < PremierLeagueManager.fClub.size();ij++){
                          if (!teamNames.contains(PremierLeagueManager.fClub.get(ij).getClubName())) {
                              teamNames.add(PremierLeagueManager.fClub.get(ij).getClubName());
                          }
                      }
                    System.out.print("ii) Enter the name the first club:");
                    String firstClub = sc.nextLine().toLowerCase();
                    random1 = teamNames.indexOf(firstClub);
                    System.out.print("iii) Enter the name of second club:");
                    String secondClub = sc.nextLine().toLowerCase();
                    random2 = teamNames.indexOf(secondClub);


                    int count3;
                    count3 = checkSameTeam(firstClub,secondClub,datePlayed);

                          if ((count3 == 0) || firstClub.isEmpty() || secondClub.isEmpty()) {

                              if (firstClub.isEmpty() || secondClub.isEmpty()) {
                                  System.out.println("");
                                  System.out.println("--------------");
                                  System.out.println("Invalid Input!"); // if user enters nothing show an error message
                                  System.out.println("--------------");
                                  System.out.println("");
                              } else {
                                  if (!firstClub.equals(secondClub)) {
                                      if (teamNames.contains(firstClub) && teamNames.contains(secondClub)) {
                                          try {
                                              System.out.print("iv) Enter the score of first club:");
                                              int score1 = sc.nextInt();
                                              System.out.print("v) Enter the score of second club:");
                                              int score2 = sc.nextInt();
                                              if (score1 >= 0 && score2 >= 0) {
                                                  plm.addPlayedMatch(firstClub, score1, secondClub, score2, datePlayed);
                                                  if (PremierLeagueManager.played.contains(score1) &&
                                                          PremierLeagueManager.played.contains(score2)) {
                                                      System.out.println("");
                                                      System.out.println("___________________");
                                                      System.out.println("Scores are updated!"); //success message
                                                      System.out.println("___________________");
                                                      System.out.println("");
                                                      playedMatches.add("|Date Played: " +
                                                              datePlayed + "|" + "\n" + "|" + firstClub.toUpperCase()
                                                              + "  " + score1 + " : " + score2 + "  " +
                                                              secondClub.toUpperCase() + "|");
                                                      datesPlayed.add(datePlayed);
                                                      changeSeason(datePlayed);
                                                      randoms.add(firstClub);
                                                      randoms.add(secondClub);
                                                      if (max > 0) {
                                                          startDate2 = "";
                                                          startDate2 += datePlayed;
                                                          max--;
                                                          maxIndex.add(max);
                                                      } else if (max == 0) {
                                                          max = 1;
                                                          maxIndex.add(max);

                                                      }
                                                      PremierLeagueManager.played.clear();
                                                  } else {
                                                      System.out.println();
                                                      //nothing
                                                  }
                                              } else { //error message when user enters negative numbers
                                                  System.out.println("");
                                                  System.out.println("---------------------------------" +
                                                          "----------------------------------");
                                                  System.out.println("Cant' update the scores " +
                                                          "because you have entered a negative score !");
                                                  System.out.println("--------------------------------" +
                                                          "-----------------------------------");
                                                  System.out.println("");
                                              }
                                          } catch (InputMismatchException ex) { //show error message when user enters a
                                              // string instead of a string
                                              System.out.println("");
                                              System.out.println("-----------------");
                                              System.out.println("Enter an Integer!");
                                              System.out.println("-----------------");
                                              System.out.println("");

                                          }
                                      }else {


                                          System.out.println("");
                                          System.out.println("----------------------------------------------------");
                                          System.out.println("|Error| - Check the clubs you entered and try again!");
                                          System.out.println("----------------------------------------------------");
                                          System.out.println("");


                                      }
                                  } else { //show an error message when user enters the same
                                      // club names for both club name inputs
                                      System.out.println("");
                                      System.out.println("------------------------------" +
                                              "----------------------------------------");
                                      System.out.println("Error cannot update scores if " +
                                              "first club and second club are the same!");
                                      System.out.println("----------------------------" +
                                              "------------------------------------------");
                                      System.out.println("");
                                  }
                              }
                          }else {  //if user enters a team which is already played on the same date
                              System.out.println("");
                              System.out.println("----------------------------------------------------");
                              System.out.println("A same team cannot be played on the same date twice!");
                              System.out.println("----------------------------------------------------");
                              System.out.println("");
                          }
                  }
            }
        }
    }







    public static void main(String[] args) throws IOException, ClassNotFoundException {
        launch(args);
    }
}
