package com.elfak.smartfarming.data.repositories.interfaces
import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.models.GraphReading
import com.elfak.smartfarming.data.models.Rule
import com.elfak.smartfarming.data.models.api.DeviceRequest
import com.elfak.smartfarming.data.models.api.GraphDataRequest
import com.elfak.smartfarming.data.models.api.RuleRequest
import com.elfak.smartfarming.domain.enums.DeviceTypes
import com.elfak.smartfarming.domain.enums.GraphPeriods


interface IDeviceRepository {
    suspend fun addDevice(device: DeviceRequest, userEmail: String, userId: String): Device
    suspend fun updateDevice(device: DeviceRequest, userEmail: String): Device
    suspend fun getAllDevices(userId: String, type: DeviceTypes? = null): List<Device>
    suspend fun getAvailableDevices(userId: String, type: DeviceTypes): List<Device>
    suspend fun getDeviceById(id: String): Device
    suspend fun getGraphData(sensorId: String, userId: String, period: GraphPeriods, graphDataRequest: GraphDataRequest): List<GraphReading>
    suspend fun removeDevice(id: String, userEmail: String, userId: String)
    suspend fun removeRule(id: String, userEmail: String)
    suspend fun getAllRules(userId: String): List<Rule>
    suspend fun getRuleByDeviceId(id: String): Rule?
    suspend fun getRuleById(id: String): Rule
    suspend fun addRule(ruleRequest: RuleRequest, email: String): Rule
    suspend fun updateRule(id: String, ruleRequest: RuleRequest, email: String): Rule
}