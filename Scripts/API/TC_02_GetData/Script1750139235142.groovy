import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import static org.assertj.core.api.Assertions.*
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import groovy.json.JsonSlurper
import internal.GlobalVariable as GlobalVariable

// Kirim request GET
RequestObject request = findTestObject('API_Endpoints/GET_GetData')
ResponseObject response = WS.sendRequest(request)

WS.verifyResponseStatusCode(response, 200)  

def responseJson = new JsonSlurper().parseText(response.getResponseBodyContent())

GlobalVariable.firstItemId = responseJson[0].id
GlobalVariable.firstItemName = responseJson[0].name

GlobalVariable.secondItemId = responseJson[1].id
GlobalVariable.secondItemName = responseJson[1].name

WS.verifyElementPropertyValue(response, '[0].id', GlobalVariable.firstItemId)
WS.verifyElementPropertyValue(response, '[0].name', GlobalVariable.firstItemName)

WS.verifyElementPropertyValue(response, '[1].id', GlobalVariable.secondItemId)
WS.verifyElementPropertyValue(response, '[1].name', GlobalVariable.secondItemName)
