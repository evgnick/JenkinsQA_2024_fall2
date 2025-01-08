package school.redrover.page.freestyle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BaseProjectPage;

import java.util.List;

public class FreestyleProjectPage extends BaseProjectPage<FreestyleProjectPage, FreestyleConfigPage, FreestyleRenamePage> {

    @FindBy(tagName = "h1")
    private WebElement projectName;

    @FindBy(xpath = "//tbody//tr[2]//td//a[contains(@class, 'display-name')]")
    private WebElement lastBuildNumber;

    @FindBy(xpath = "//tbody/tr[2]/td/div[2]/a")
    private WebElement lastBuildDateTime;

    @FindBy(tagName = "h1")
    private WebElement workspaceTitle;

    @FindBy(xpath = "//tr")
    private List<WebElement> listOfBuilds;

    @FindBy(tagName = "dialog")
    private List<WebElement> wipeOutCurrentWorkspaceDialog;

    @FindBy(css = "dialog *")
    private List<WebElement> wipeOutCurrentWorkspaceDialogOptions;

    @FindBy(xpath = "//span[text()='Rename']/..")
    private WebElement renameSidebar;

    @FindBy(xpath = "//a[contains(@href, 'configure')]")
    private WebElement configureSidebar;

    @FindBy(xpath = "//a[@data-build-success='Build scheduled']")
    private WebElement buildNowSidebar;

    @FindBy(xpath = "//tbody//tr[2]//a")
    private WebElement lastBuildSuccessBuildIcon;

    @FindBy(xpath = "//a[@data-title='Delete Project']")
    private WebElement deleteProjectSidebar;

    @FindBy(xpath = "//button[@data-id='ok']")
    private WebElement yesButton;

    @FindBy(xpath = "//span[text()='Workspace']/..")
    private WebElement workspaceSidebar;

    @FindBy(xpath = "//a[@data-title='Wipe Out Current Workspace']")
    private WebElement wipeOutCurrentWorkspaceSidebar;

    @FindBy(xpath = "//*[@id='breadcrumbs']/li[5]")
    private WebElement breadCrumbs;

    @FindBy(name = "Submit")
    private WebElement enableButton;

    @FindBy(id = "enable-project")
    private WebElement projectStatusText;

    public FreestyleProjectPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public FreestyleConfigPage createProjectConfigPage() {
        return new FreestyleConfigPage(getDriver());
    }

    @Override
    public FreestyleRenamePage createProjectRenamePage() {
        return new FreestyleRenamePage(getDriver());
    }

    public String getProjectName() {
        return projectName.getText();
    }

    public String getLastBuildNumber() {
        return lastBuildNumber.getText();
    }

    public FreestyleBuildStatusPage clickLastBuildDateTime(){
        getWait10().until(ExpectedConditions.elementToBeClickable(lastBuildDateTime)).click();
        return new FreestyleBuildStatusPage(getDriver());
    }

    public String getWorkspaceTitle() {
        return workspaceTitle.getText();
    }

    public FreestyleProjectPage clickWorkspaceSidebar() {
        getWait10().until(ExpectedConditions.visibilityOf(workspaceSidebar)).click();

        return this;
    }

    public FreestyleProjectPage clickWipeOutCurrentWorkspaceSidebar() {
        getWait10().until(ExpectedConditions.visibilityOf(wipeOutCurrentWorkspaceSidebar)).click();

        return this;
    }

    public FreestyleProjectPage clickBuildNowSidebar() {
        getWait5().until(ExpectedConditions.elementToBeClickable(buildNowSidebar)).click();

        return this;
    }

    public List<String> getListOfBuilds() {
        return getWait5().until(ExpectedConditions.visibilityOfAllElements(listOfBuilds))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public boolean verifyConfirmationDialogOptionsPresence(List<String> dialogOptions) {
        getWait10().until(ExpectedConditions.visibilityOfAllElements(wipeOutCurrentWorkspaceDialog));
        List<String> confirmationDialogOptions = wipeOutCurrentWorkspaceDialogOptions.stream()
                .map(WebElement::getText)
                .toList();

        for (String option : dialogOptions) {
            if (!confirmationDialogOptions.contains(option))
                return false;
        }
        return true;
    }

    public FreestyleBuildPage clickOnSuccessBuildIconForLastBuild() {
        getWait10().until(ExpectedConditions.visibilityOf(lastBuildSuccessBuildIcon)).click();

        return new FreestyleBuildPage(getDriver());
    }

    public FreestyleProjectPage clickYesToWipeOutCurrentWorkspace() {
        getWait10().until(ExpectedConditions.elementToBeClickable(yesButton)).click();
        return this;
    }

    public FreestyleConfigPage changeEnablingStateViaIndicator() {
        getWait10().until(ExpectedConditions.visibilityOf(enableButton)).click();

        return new FreestyleConfigPage(getDriver());
    }

    public String getBreadCrumb() {
        return breadCrumbs.getText();
    }

    public String getDisabledProjectIndicator() {
        return getWait10().until(ExpectedConditions.visibilityOf(projectStatusText)).getText();
    }
}
