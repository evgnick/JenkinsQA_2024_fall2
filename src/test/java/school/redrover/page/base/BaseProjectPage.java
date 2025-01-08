package school.redrover.page.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.home.CreateNewItemPage;
import school.redrover.page.home.HomePage;
import school.redrover.runner.TestUtils;

import java.util.List;

public abstract class BaseProjectPage<Self extends BaseProjectPage<?, ?, ?>, ProjectConfigPage, ProjectRenamePage> extends BasePage {

    @FindBy(id = "description-link")
    private WebElement descriptionButton;

    @FindBy(xpath = "//span[text()='New Item']/ancestor::a")
    private WebElement newItem;

    @FindBy(name = "description")
    private WebElement descriptionField;

    @FindBy(name = "Submit")
    private WebElement submitButton;

    @FindBy(xpath = "//div[@id='description']/div[1]")
    private WebElement descriptionText;

    @FindBy(xpath = "//a[contains(@href,'rename')]")
    private WebElement renameButtonViaSidebar;

    @FindBy(name = "newName")
    private WebElement newNameField;

    @FindBy(xpath = "//div[@id='main-panel']/p")
    private WebElement errorMessage;

    @FindBy(xpath = "//div[@class='task ']//span[2]")
    private List<WebElement> sidebarElementList;

    @FindBy(xpath = "//*[@id='main-panel']/h1")
    private WebElement itemName;

    @FindBy(className = "textarea-show-preview")
    private WebElement previewOption;

    @FindBy(className = "textarea-preview")
    private WebElement previewDescriptionText;

    @FindBy(xpath = "//a[@id='description-link']/text()")
    private WebElement descriptionButtonText;

    @FindBy(css = "[class*='task-link-wrapper'] [href$='/configure']")
    private WebElement sidebarConfigureButton;

    @FindBy(xpath = "//a[contains(@href, 'confirm-rename')]")
    private WebElement renameSidebarButton;

    @FindBy(xpath = "//span[contains(text(),'Delete')]")
    private WebElement deleteButtonSidebar;

    @FindBy(xpath = "//button[@data-id='ok']")
    private WebElement yesButton;

    @FindBy(xpath = "//button[@class='jenkins-menu-dropdown-chevron'][contains(@data-href,'job')]")
    private WebElement chevronButton;

    @FindBy(xpath = "//button[contains(@href,'Delete')]")
    private WebElement deleteBreadcrumbButton;

    @FindBy(xpath = "//div[@class='jenkins-dropdown']//a[contains(@href,'rename')]")
    private WebElement renameBreadcrumbButton;

    public BaseProjectPage(WebDriver driver) {
        super(driver);
    }

    protected abstract ProjectConfigPage createProjectConfigPage();

    protected abstract ProjectRenamePage createProjectRenamePage();

    public CreateNewItemPage clickNewItem() {
        newItem.click();

        return new CreateNewItemPage(getDriver());
    }

    public Self editDescription(String text) {
        descriptionButton.click();
        descriptionField.clear();
        descriptionField.sendKeys(text);

        return (Self) this;
    }

    public Self clickSubmitButton() {
        submitButton.click();

        return (Self) this;
    }

    public Self clearDescription() {
        descriptionButton.click();
        descriptionField.clear();
        submitButton.click();

        return (Self) this;
    }

    public Self clickPreview() {
        previewOption.click();

        return (Self) this;
    }

    public Self renameItem(String newName) {
        renameButtonViaSidebar.click();
        newNameField.clear();
        newNameField.sendKeys(newName);
        submitButton.click();
        return (Self) this;
    }

    public String getPreviewDescriptionText() {
        return previewDescriptionText.getText();
    }

    public String getRenameWarningMessage() {
        return getWait10().until(ExpectedConditions.visibilityOf(errorMessage)).getText();
    }

    public String getItemName() {
        return getWait10().until(ExpectedConditions.visibilityOf(itemName)).getText();
    }

    public String getDescription() {
        return descriptionText.getText();
    }

    public String getDescriptionButtonText() {
        return descriptionButton.getText();
    }

    public List<String> getSidebarOptionList() {
        return getWait5().until(ExpectedConditions.visibilityOfAllElements(sidebarElementList))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public ProjectConfigPage clickSidebarConfigButton() {
        getWait2().until(ExpectedConditions.elementToBeClickable(sidebarConfigureButton)).click();

        return createProjectConfigPage();
    }

    public HomePage clickDeleteButtonSidebarAndConfirm() {
        getWait2().until(ExpectedConditions.elementToBeClickable(deleteButtonSidebar)).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(yesButton)).click();

        return new HomePage(getDriver());
    }

    public ProjectRenamePage clickRenameSidebarButton() {
        getWait2().until(ExpectedConditions.elementToBeClickable(renameSidebarButton)).click();

        return createProjectRenamePage();
    }

    public Self openBreadcrumbDropdown() {
        TestUtils.moveAndClickWithJS(getDriver(), chevronButton);

        return (Self) this;
    }

    public HomePage clickDeleteBreadcrumbDropdownAndConfirm() {
        deleteBreadcrumbButton.click();
        yesButton.click();

        return new HomePage(getDriver());
    }

    public ProjectRenamePage clickRenameBreadcrumbDropdown(){
        renameBreadcrumbButton.click();

        return createProjectRenamePage();
    }
}
