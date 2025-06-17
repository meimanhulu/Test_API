<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description></description>
   <name>GET_GetData</name>
   <tag></tag>
   <elementGuidId>f10d06ec-0a26-46f5-931a-de58c2e53d04</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <smartLocatorEnabled>false</smartLocatorEnabled>
   <useRalativeImagePath>false</useRalativeImagePath>
   <autoUpdateContent>false</autoUpdateContent>
   <connectionTimeout>0</connectionTimeout>
   <followRedirects>true</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent></httpBodyContent>
   <httpBodyType></httpBodyType>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>application/json</value>
      <webElementGuid>e3e4ec73-4642-4089-837e-7961a2ce65f4</webElementGuid>
   </httpHeaderProperties>
   <katalonVersion>10.2.1</katalonVersion>
   <maxResponseSize>0</maxResponseSize>
   <migratedVersion>5.4.1</migratedVersion>
   <path></path>
   <restRequestMethod>GET</restRequestMethod>
   <restUrl>http://localhost:3000/items</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceEndpoint></soapServiceEndpoint>
   <soapServiceFunction></soapServiceFunction>
   <socketTimeout>0</socketTimeout>
   <useServiceInfoFromWsdl>true</useServiceInfoFromWsdl>
   <verificationScript>import static org.assertj.core.api.Assertions.*
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webservice.verification.WSResponseManager
import groovy.json.JsonSlurper
import internal.GlobalVariable as GlobalVariable

// Kirim request GET
RequestObject request = findTestObject('APIEndpoints/GET_GetData')
ResponseObject response = WS.sendRequest(request)

// Verifikasi Status Code untuk GET request
WS.verifyResponseStatusCode(response, 200)  // Memastikan status code 200 (OK) untuk GET

// Verifikasi elemen pada respons JSON dengan menggunakan GlobalVariable yang telah disimpan
def responseJson = new JsonSlurper().parseText(response.getResponseBodyContent())
GlobalVariable.firstItemId = responseJson[0].id
GlobalVariable.firstItemName = responseJson[0].name

GlobalVariable.secondItemId = responseJson[1].id
GlobalVariable.secondItemName = responseJson[1].name

WS.verifyElementPropertyValue(response, '[0].id', GlobalVariable.firstItemId)
WS.verifyElementPropertyValue(response, '[0].name', GlobalVariable.firstItemName)

WS.verifyElementPropertyValue(response, '[1].id', GlobalVariable.secondItemId)
WS.verifyElementPropertyValue(response, '[1].name', GlobalVariable.secondItemName)</verificationScript>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>
