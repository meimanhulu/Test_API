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


Map<String, String> itemMap = [:]

RequestObject request = findTestObject('API_Endpoints/GET_GetData')
ResponseObject response = WS.sendRequest(request)

if (response != null) {
    println "Response diterima: " + response.getResponseBodyContent() // Menampilkan isi response untuk debugging
    WS.verifyResponseStatusCode(response, 200)  

    def responseJson = new JsonSlurper().parseText(response.getResponseBodyContent())

    if (responseJson instanceof List) {
        for (int i = 0; i < responseJson.size(); i++) {
            def item = responseJson[i]
       
            if (item.containsKey('id') && item.containsKey('name')) {
                String itemId = item.id
                String itemName = item.name
                itemMap["itemId${i}"] = itemId
                itemMap["itemName${i}"] = itemName
                WS.verifyElementPropertyValue(response, "[${i}].id", itemMap["itemId${i}"])
                WS.verifyElementPropertyValue(response, "[${i}].name", itemMap["itemName${i}"])
            } else {
                println "Error: 'id' atau 'name' tidak ditemukan di item index ${i}"
            }
        }
    } else {
        println "Error: Respons bukan array yang diharapkan"
        assert false : "Respons tidak mengandung array"
    }
} else {
    println "Error: Response is null" // Log error jika respons null
    assert false : "Tidak ada respons yang diterima dari API"
}
