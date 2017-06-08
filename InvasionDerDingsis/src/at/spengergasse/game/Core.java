package at.spengergasse.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.IntBuffer;

import at.spengergasse.enemyInput.InvadersInput;
import at.spengergasse.enemyInput.RoundInput;
import at.spengergasse.input.Keyboard;
import at.spengergasse.input.MenuKeyboard;
import at.spengergasse.mode.InvadersMode;
import at.spengergasse.mode.Mode;
import at.spengergasse.mode.RoundMode;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Core brings all together.
 * It managed the whole game and makes the Menu. 
 */
public class Core extends Application {

	private Stage primaryStage;

	// game
	private int screenX, screenY;

	private Group groupG;
	private Scene sceneG;

	private Canvas canvas;
	private WritableImage image;
	private PixelWriter pixelWriter;

	private GraphicsContext graphicsContext;
	private PixelFormat<IntBuffer> pixelFormat;

	private AnimationTimer loop;

	private Mode mode;

	private Keyboard keyboard;

	private int[] data;

	// menü
	private Scene sceneM;

	private MenuKeyboard keyboardM;

	private int menuState;

	private HBox hBoxStart;
	// private HBox hBoxOptions;
	private HBox hBoxQuitGame;

	private Scene sceneMs;

	private int menuStateS;

	private HBox[][] levels;

	private boolean menuStateL;

	private HBox top1, top2;
	private FlowPane fPane;

	private ProgressBar healthBar;
	private ProgressBar bossBar;
		
	private Label finishScreen,finishScreen2,finishScreen3;

	/**
	 * Initiate the window, the menu, Healthbars, endscreens...
	 */
	public void init() {
		screenX = 1440;
		screenY = 810;

		// game
		data = new int[screenX * screenY];

		groupG = new Group();
		sceneG = new Scene(groupG, screenX, screenY);

		healthBar = new ProgressBar();
		healthBar.relocate(30, screenY - 35);
		healthBar.setMinSize(200, 15);
		healthBar.setStyle("-fx-accent: red; -fx-padding: 0;-fx-background: black;");
		
		bossBar = new ProgressBar();
		bossBar.relocate(screenX/2 - 250, 20);
		bossBar.setMinSize(500, 30);
		bossBar.setStyle("-fx-accent: red; -fx-padding: 0;-fx-background: black;");

		pixelFormat = PixelFormat.getIntArgbInstance();
		image = new WritableImage(screenX, screenY);
		canvas = new Canvas(screenX, screenY);
		pixelWriter = image.getPixelWriter();

		graphicsContext = canvas.getGraphicsContext2D();

		groupG.getChildren().add(canvas);

		keyboard = new Keyboard();
		sceneG.addEventHandler(KeyEvent.KEY_PRESSED, keyboard);
		sceneG.addEventHandler(KeyEvent.KEY_RELEASED, keyboard);
		
		loop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if(mode.finished()!=0){
					finish(mode.finished());
				}
				healthBar.setProgress( mode.getPlayer().getHealth() / 100);
				if(mode.getBoss() != null){
					bossBar.setProgress( mode.getBoss().getHealth() / 2000);
				}
				mode.update();
				draw();
			}
		};

		// menü

		Pane pane = new Pane();

		keyboardM = new MenuKeyboard(this);

		sceneM = new Scene(pane, screenX, screenY);
		sceneM.addEventHandler(KeyEvent.KEY_PRESSED, keyboardM);
		sceneM.addEventHandler(KeyEvent.KEY_RELEASED, keyboardM);

		pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

		HBox hBoxTitel = new HBox();
		Image imageTitel = new Image("file:src/menu/iddMenu.jpg");
		hBoxTitel.getChildren().add(new ImageView(imageTitel));
		hBoxTitel.relocate(screenX / 2 - imageTitel.getWidth() / 2, 50);
		pane.getChildren().add(hBoxTitel);

		hBoxStart = new HBox();
		Image imageStart = new Image("file:src/menu/startGame.jpg");
		hBoxStart.getChildren().add(new ImageView(imageStart));
		hBoxStart.relocate(screenX / 2 - imageStart.getWidth() / 2, screenY / 2 - 100);
		hBoxStart.setBorder(
				new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, new BorderWidths(5))));
		pane.getChildren().add(hBoxStart);

		hBoxQuitGame = new HBox();
		Image imageQuitGame = new Image("file:src/menu/quitGame.jpg");
		hBoxQuitGame.getChildren().add(new ImageView(imageQuitGame));
		hBoxQuitGame.relocate(screenX / 2 - imageQuitGame.getWidth() / 2, screenY / 2 + 50);
		pane.getChildren().add(hBoxQuitGame);

		Pane paneS = new Pane();

		sceneMs = new Scene(paneS, screenX, screenY);

		sceneMs.addEventHandler(KeyEvent.KEY_PRESSED, keyboardM);
		sceneMs.addEventHandler(KeyEvent.KEY_RELEASED, keyboardM);

		paneS.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

		fPane = new FlowPane();
		paneS.getChildren().add(fPane);
		fPane.relocate(10, 300);
		fPane.setPrefWidth(screenX - 20);

		HBox top = new HBox();
		paneS.getChildren().add(top);
		top.relocate(screenX / 2 - 200, 1);

		top1 = new HBox();
		top2 = new HBox();
		top.getChildren().addAll(top1, top2);// top.getChildren().addAll(top1,top2,top3);

		top1.getChildren().add(new ImageView(new Image("file:src/menu/invadersMenu.jpg")));
		top1.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, new BorderWidths(5))));
		top2.getChildren().add(new ImageView(new Image("file:src/menu/360Menu.jpg")));

		levels = new HBox[2][10];

		fPane.setHgap(150);
		fPane.setVgap(50);
		fPane.setPadding(new Insets(50, 50, 50, 140));
		
		groupG.getChildren().add(healthBar);
		groupG.getChildren().add(bossBar);
		
		finishScreen = new Label("Level Finished");
		finishScreen.setStyle("-fx-text-fill: white;");
		
		finishScreen.relocate(screenX/2-300, screenY/2-200);
		finishScreen.addEventHandler(KeyEvent.KEY_PRESSED, keyboardM);
		finishScreen.addEventHandler(KeyEvent.KEY_RELEASED, keyboardM);
		
		finishScreen2 = new Label("Press Enter to return");
		finishScreen2.setStyle("-fx-text-fill: white;");
		
		finishScreen2.relocate(screenX/2-300, screenY/2-110);
		
		finishScreen3 = new Label("Level Failed");
		finishScreen3.setStyle("-fx-text-fill: white;");
		
		finishScreen3.relocate(screenX/2-300, screenY/2-200);
		finishScreen3.addEventHandler(KeyEvent.KEY_PRESSED, keyboardM);
		finishScreen3.addEventHandler(KeyEvent.KEY_RELEASED, keyboardM);
		
		try {
			Font myFont = Font.loadFont(new FileInputStream(new File("src/menu/BACKTO1982.TTF")), 50);
			Font myFont2 = Font.loadFont(new FileInputStream(new File("src/menu/BACKTO1982.TTF")), 20);
			finishScreen3.setFont(myFont);
			finishScreen2.setFont(myFont2);
			finishScreen.setFont(myFont);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Makes the Window with Icon, Title and set the Menu scene.
	 * 
	 * @param primaryStage the current PrimaryStage
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		primaryStage.setWidth(screenX + 6);
		primaryStage.setHeight(screenY + 29);

		primaryStage.setScene(sceneM);

		primaryStage.setResizable(false);
		primaryStage.setTitle("Invasion der Dingsis");

		primaryStage.getIcons().add(new Image("file:src/menu/icon.jpg"));

		primaryStage.show();

	}

	/**
	 * Handles the menu.
	 * Makes the Border on the field which you currently have selected.
	 * 
	 * @param e the movement in the menu (1 = up; -1 = down)
	 */
	public void menu(int e) {
		if (!menuStateL) {
			menuState += e;
			if (menuState > 1) {
				menuState = 0;
			} else if (menuState < 0) {
				menuState = 1;
			}
		}

		menuStateS += e;
		if (menuStateS > 9) {
			menuStateS = 0;
		} else if (menuStateS < 0) {
			menuStateS = 9;
		}

		if (primaryStage.getScene() == sceneM) {
			switch (menuState) {
			case 0:
				hBoxStart.setBorder(
						new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, new BorderWidths(5))));
				hBoxQuitGame.setBorder(null);
				break;// hBoxOptions.setBorder(null)
			case 1:
				hBoxQuitGame.setBorder(
						new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, new BorderWidths(5))));
				hBoxStart.setBorder(null);
				break;// hBoxOptions.setBorder(null);
			}
		} else if (primaryStage.getScene() == sceneMs && !menuStateL) {
			switch (menuState) {
			case 0:
				top1.setBorder(
						new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, new BorderWidths(5))));
				top2.setBorder(null);
				break;
			case 1:
				top2.setBorder(
						new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, new BorderWidths(5))));
				top1.setBorder(null);
				break;
			}
		} else {
			for (int h = 0; h < levels[menuState].length; h++)
				levels[menuState][h].setBorder(null);
			levels[menuState][menuStateS].setBorder(
					new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, new BorderWidths(5))));
		}
	}
	
	/**
	 * Stops the game loop and shows the finish screen if the fame is finished.
	 * 
	 * @param modeFinish 1 = win; -1 = lose
	 */
	private void finish(int modeFinish){
		if(modeFinish==1){
			loop.stop();
			groupG.getChildren().add(finishScreen);
			groupG.getChildren().add(finishScreen2);
			finishScreen.requestFocus();
		}else{
			loop.stop();
			groupG.getChildren().add(finishScreen3);
			groupG.getChildren().add(finishScreen2);
			finishScreen3.requestFocus();
		}
	}

	/**
	 * Loads the next screen which you have selected in the menu.
	 */
	public void enter() {
		bossBar.setOpacity(100);
		if (primaryStage.getScene() == sceneM) {
			if (menuState == 0) {
				primaryStage.setScene(sceneMs);
			} else if (menuState == 1) {
				System.exit(0);
			}
		} else if (primaryStage.getScene() == sceneMs) {
			if (!menuStateL) {
				menuStateL = true;
				loadLevels(menuState);
				fPane.getChildren().clear();
				fPane.getChildren().addAll(levels[menuState]);
			} else {
				if (menuState == 0) {
					keyboard.clean();
					try {
						mode = new InvadersMode(screenX, screenY, data, keyboard, menuStateS + 1,new InvadersInput());
					} catch (NumberFormatException | IOException e) {
						e.printStackTrace();
					}
					if(menuStateS +1 != 10 && menuState == 0){
						bossBar.setOpacity(0);
					}
					primaryStage.setScene(sceneG);
					loop.start();
				} else {
					keyboard.clean();
					try {
						mode = new RoundMode(screenX, screenY, data, keyboard, menuStateS + 1,new RoundInput());
					} catch (NumberFormatException | IOException e) {
						e.printStackTrace();
					}
					if(menuStateS +1 != 10){
						bossBar.setOpacity(0);
					}
					primaryStage.setScene(sceneG);
					loop.start();
				}
			}
		}
		else if (primaryStage.getScene()==sceneG){
				primaryStage.setScene(sceneM);
				menuState=0;
				menuStateL=false;
				groupG.getChildren().remove(finishScreen);
				groupG.getChildren().remove(finishScreen2);
				groupG.getChildren().remove(finishScreen3);
				fPane.getChildren().clear();
		}
		menuStateS = 0;
		menu(0);
	}

	/**
	 * Loads images of all levels in the menu.
	 * 
	 * @param e the number of levels
	 */
	private void loadLevels(int e) {
		for (int i = 0; i < levels[e].length; i++) {// TODO
			HBox box = new HBox();
			box.getChildren().add(new ImageView(new Image("file:src/menu/levels/level" + (i + 1) + ".jpg")));
			levels[e][i] = box;
		}
	}

	/**
	 * Loads the previous screen in the menu if you press escape.
	 */
	public void escape() {
		if (primaryStage.getScene() != sceneM) {
			if (primaryStage.getScene() == sceneMs) {
				if (menuStateL) {
					fPane.getChildren().clear();
					menuStateL = false;
				} else {
					primaryStage.setScene(sceneM);
				}
			}
			menuStateS = 0;
			menu(0);
		}
	}

	/**
	 * Draws the Entities.
	 */
	private void draw() {
		pixelWriter.setPixels(0, 0, screenX, screenY, pixelFormat, data, 0, screenX);
		graphicsContext.drawImage(image, 0, 0);
	}

}
