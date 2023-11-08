const getDevices = async (type) => {
    return []
 }

const getDevice = async (id) => { return true }

const addDevice = async (device) => { return true }

const updateDevice = async (device) => { return true }

const removeDevice = async (id) => { return true }

const getRuleFromDeviceId = async (id) => { return true }

const addRule = async (rule) => { return true }

const updateRule = async (rule) => { return true }

const removeRule = async (id) => { return true }

export default { 
    getDevices,
    getDevice,
    addDevice,
    updateDevice,
    removeDevice,

    getRuleFromDeviceId,
    addRule,
    updateRule,
    removeRule,

    saveSensorData, 
    saveAlert
}

