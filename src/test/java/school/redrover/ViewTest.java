package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.page.NewViewPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;
import java.util.Map;

public class ViewTest extends BaseTest {

    private static final String PIPELINE_NAME = "PipelineName";
    private static final String VIEW_NAME = "ViewName";

    @Test
    public void testCreateListViewForSpecificJob() {
        TestUtils.createPipelineProject(getDriver(), PIPELINE_NAME);

        List<String> viewList = new HomePage(getDriver())
                .clickCreateNewViewButton()
                .typeNameIntoInputField(VIEW_NAME)
                .selectListViewType()
                .clickCreateButton()
                .selectJobCheckBoxByName(PIPELINE_NAME)
                .clickOkButton()
                .gotoHomePage()
                .getViewList();

        Assert.assertListContainsObject(
                viewList,
                VIEW_NAME,
                "New List View is displayed");
    }

    @Test(dependsOnMethods = "testCreateListViewForSpecificJob")
    public void testVerifyDescriptionsForViewTypes() {
        final Map<String, String> expectedDescriptions = Map.of(
                "List View",
                "Shows items in a simple list format. You can choose which jobs are to be displayed in which view.",
                "My View",
                "This view automatically displays all the jobs that the current user has an access to."
        );

        Map<String, String> actualDescriptions = new HomePage(getDriver())
                .clickCreateNewViewButton()
                .getTypeToDescriptionMap();

        expectedDescriptions.forEach((expectedType, expectedDescription) -> {
            String actualDescription = actualDescriptions.get(expectedType);

            Assert.assertNotNull(
                    actualDescription, "Description is missing for type: " + expectedType);
            Assert.assertEquals(actualDescription, expectedDescription,
                    "Description does not match for type: " + expectedType);
        });
    }

    @Test(dependsOnMethods = "testVerifyDescriptionsForViewTypes")
    public void testDeleteViewColumnForSpecificProjectByXButton() {
        final String columnName = "Weather";

        List<String> columnList = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME)
                .clickEditView(VIEW_NAME)
                .clickDeleteColumnByName(columnName)
                .clickOkButton()
                .getColumnList();

        Assert.assertEquals(columnList.size(), 6);
        Assert.assertListNotContainsObject(columnList, columnName, "Deleted column is not displayed");
    }

    @Test(dependsOnMethods = "testDeleteViewColumnForSpecificProjectByXButton")
    public void testAddColumn() {
        final String columnName = "Git Branches";

        List<String> columnList = new HomePage(getDriver())
                .clickViewByName(VIEW_NAME)
                .clickEditView(VIEW_NAME)
                .clickAddColumn()
                .selectColumnByName(columnName)
                .clickOkButton()
                .getColumnList();

        Assert.assertEquals(columnList.size(), 7);
        Assert.assertTrue(columnList.contains(columnName));
    }

    @Test
    public void testCreateNewViewForm() {
        TestUtils.createFolder(getDriver(), "NewFolder");

        NewViewPage newViewPage = new HomePage(getDriver())
                .gotoHomePage()
                .clickCreateNewViewButton();

        Assert.assertTrue(newViewPage.getInputFromNameField().isEmpty(), "Input field should be empty.");
        Assert.assertFalse(newViewPage.isRadioButtonListViewSelected(), "ListView radio button should not be selected.");
        Assert.assertFalse(newViewPage.isRadioButtonMyViewSelected(), "MyView radio button should not be selected.");
        Assert.assertFalse(newViewPage.isCreateButtonEnabled(), "Create button should be disabled.");
    }
}