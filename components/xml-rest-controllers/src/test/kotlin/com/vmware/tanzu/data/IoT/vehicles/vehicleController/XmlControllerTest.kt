package com.vmware.tanzu.data.IoT.vehicles.vehicleController

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.function.Consumer

@ExtendWith(MockitoExtension::class)
internal class XmlControllerTest {

    @Mock
    private lateinit var mockConsumer : Consumer<String>;

    @Test
    fun load() {
        val subject = XmlController(mockConsumer);
        val xml = "<volvo>\n" +
                "    <VehicleMessages xmlns=\"http://ps.volvo.com/services/VehicleMessages/1_0\">\n" +
                "        <Message id=\"370765528\">\n" +
                "            <VehicleLocation>\n" +
                "                <Vin>4V4NC9EH0GN938727</Vin>\n" +
                "                <Timestamp>2018-02-13T23:53: 50.000Z</Timestamp>\n" +
                "                <Latitude>28.327771</Latitude>\n" +
                "                <Longitude>-82.321893</Longitude>\n" +
                "                <Altitude>105</Altitude>\n" +
                "                <Heading>12</Heading>\n" +
                "                <Odometer>335797.667</Odometer>\n" +
                "                <EngineSpeed>608</EngineSpeed>\n" +
                "                <RoadSpeed>0</RoadSpeed>\n" +
                "                <EngineRunning>true</EngineRunning>\n" +
                "                <Event>STOP</Event>\n" +
                "                <IdleEngineHours>4288.456</IdleEngineHours>\n" +
                "                <TotalFuelUsed>52526.385859</TotalFuelUsed>\n" +
                "            </VehicleLocation>\n" +
                "        </Message></VehicleMessages></volvo>";


        subject.load(xml);
        Mockito.verify(mockConsumer).accept(any());
    }
}