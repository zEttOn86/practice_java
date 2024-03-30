package swtbot.sample.views.tests;
import static org.eclipse.swtbot.swt.finder.waits.Conditions.shellCloses;

import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class SampleViewTest {

	private static SWTWorkbenchBot	bot;

	@BeforeClass
	public static void beforeClass() {
		bot = new SWTWorkbenchBot();
		bot.viewByTitle("Welcome").close();

	}


//	@Test
//	public void canCreateANewJavaProject() throws Exception {
//		bot.menu("File").menu("New").menu("Project...").click();
//
//		SWTBotShell shell = bot.shell("New Project");
//		shell.activate();
//		bot.tree().expandNode("Java").select("Java Project");
//		bot.button("Next >").click();
//
//		bot.textWithLabel("Project name:").setText("MyFirstProject");
//
//		bot.button("Finish").click();
//		// FIXME: assert that the project is actually created, for later
//	}
	@Test
	public void testSampleViewInJavaPerspective()  {


		Display.getDefault().asyncExec(new Runnable() {
		    @Override
		    public void run() {
		    	try {

		    		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("swtbot.sample.views.views.SampleView");
		    	}catch (PartInitException e) {
					// TODO Auto-generated catch block
//						((Object) e).printStackTrace();
				}
		    }
		});

		List<SWTBotView>  views = bot.views();
		for(SWTBotView i:views) {
			System.out.println(i.getTitle());
		}
		bot.viewById("swtbot.sample.views.views.SampleView").show();
		System.out.print("dd");
	}

//	@Test
//	public void canCreateSampleView() throws Exception{
////		bot.menu("Window").menu("Show View").menu("Other...").click();
//		System.setProperty("org.eclipse.swtbot.playback.delay", "1200");
//		bot.menu("Window").menu("Show View").menu("Other...").click();
////		bot.tree().
//		SWTBotView view = bot.viewByTitle("Sample View");
//		view.bot().table().getTableItem("One").doubleClick();
//		assertDialog("Double-click detected on TWo");
//	}

	private void assertDialog(String labelInDialog) {
		SWTBotShell dialog = bot.shell("Sample View");
		dialog.activate();
		bot.label(labelInDialog);
		bot.button("OK").click();
		bot.waitUntil(shellCloses(dialog));
	}

	@AfterClass
	public static void sleep() {
		bot.sleep(2000);
	}

}