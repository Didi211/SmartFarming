package com.elfak.smartfarming.ui.screens.ruleDetailsScreen

import com.elfak.smartfarming.data.models.Device
import com.elfak.smartfarming.data.models.Rule
import com.elfak.smartfarming.data.models.ToastData
import com.elfak.smartfarming.domain.enums.ScreenState

data class RuleDetailsUiState(
    val screenState: ScreenState = ScreenState.View,
    val toastData: ToastData = ToastData(),
    val rule: Rule = Rule(),
    val ruleId: String? = null,
    val sensor: Device = Device(),
    val actuator: Device = Device(),
    val ruleActions: Map<String, (Any?) -> Unit> = emptyMap(),
    val selectSensors: List<Device> = emptyList(),
    val selectActuators: List<Device> = emptyList(),

)