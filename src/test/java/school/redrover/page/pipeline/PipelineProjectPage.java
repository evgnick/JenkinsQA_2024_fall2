package school.redrover.page.pipeline;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BaseProjectPage;

import java.util.List;

public class PipelineProjectPage extends BaseProjectPage<PipelineProjectPage, PipelineConfigurePage, PipelineRenamePage> {

    @FindBy(xpath = "//form[@id='enable-project']")
    private WebElement warningDisabledMessage;

    @FindBy(xpath = "//form[@id='enable-project']/button[@name='Submit']")
    private WebElement enableDisableStatusButton;

    @FindBy(css = "a[data-build-success='Build scheduled']")
    private WebElement buildNowSidebarButton;

    @FindBy(css = "a[tooltip$='> Console Output']")
    private WebElement buildIcon;

    @FindBy(css = "a[href$='multi-pipeline-graph']")
    private WebElement stagesSidebarButton;

    @FindBy(xpath = "//div[@class='jenkins-app-bar']//h1")
    private WebElement projectPageTitle;

    @FindBy(xpath = "//ol[@id='breadcrumbs']/li[@class='jenkins-breadcrumbs__list-item'][2]")
    private WebElement projectNameBreadcrumbs;

    @FindBy(xpath = "//*[local-name()='svg' and @tooltip]")
    private WebElement buildStatusMark;

    @FindBy(xpath = "//div[@class='tippy-content']")
    private WebElement statusMarkTooltip;

    @FindBy(xpath = "//li[@class='permalink-item']")
    private List<WebElement> permalinks;

    public PipelineProjectPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public PipelineConfigurePage createProjectConfigPage() {
        return new PipelineConfigurePage(getDriver());
    }

    @Override
    public PipelineRenamePage createProjectRenamePage() {
        return new PipelineRenamePage(getDriver());
    }

    public String getWarningDisabledMessage() {
        return getWait5().until(ExpectedConditions.visibilityOf(warningDisabledMessage)).getText().split("\n")[0];
    }

    public String getStatusButtonText() {
        return getWait2().until(ExpectedConditions.visibilityOf(enableDisableStatusButton)).getText();
    }

    public PipelineProjectPage clickOnBuildNowItemOnSidePanelAndWait() {
        buildNowSidebarButton.click();
        getWait5().until(ExpectedConditions.visibilityOf(buildIcon));

        return this;
    }

    public PipelineStagesPage clickOnStagesItemOnSidePanel() {
        stagesSidebarButton.click();

        return new PipelineStagesPage(getDriver());
    }

    public String getTitle() {
        return projectPageTitle.getText();
    }

    public String getProjectNameBreadcrumb() {
        return projectNameBreadcrumbs.getText();
    }

    public PipelineProjectPage clickEnableButton() {
        getWait2().until(ExpectedConditions.elementToBeClickable(enableDisableStatusButton)).click();

        return this;
    }

    public PipelineProjectPage hoverOverBuildStatusMark() {
        new Actions(getDriver())
                .moveToElement(getWait10().until(ExpectedConditions.visibilityOf(buildStatusMark)))
                .perform();

        return this;
    }

    public String getStatusMarkTooltipText() {
        return getWait10().until(ExpectedConditions.visibilityOf(statusMarkTooltip)).getText();
    }

    public PipelineBuildPage clickBuildStatusMark() {
        getWait10().until(ExpectedConditions.elementToBeClickable(buildStatusMark)).click();

        return new PipelineBuildPage(getDriver());
    }

    public List<String> getPermalinkList() {
        return getWait10().until(ExpectedConditions.visibilityOfAllElements(permalinks))
                .stream()
                .map(WebElement::getText)
                .map(string -> string.split("\\(#")[0].trim())
                .toList();
    }

    public PipelineSyntaxPage gotoPipelineSyntaxPageFromLeftPanel(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/job/%s/pipeline-syntax']".formatted(projectName))).click();

        return new PipelineSyntaxPage(getDriver());
    }
}
