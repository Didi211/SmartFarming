import { Point } from "@influxdata/influxdb-client";
import { writeClient } from "../config/influxdb-config.js";

const saveToDb = async (sensorId, reading) => { 
    try {
        let point = new Point('sensor-data')
            .tag('sensor-id', sensorId)
            .floatField('reading', reading);
            writeClient.writePoint(point);
            writeClient.flush();
    } catch (error) {
        throw { 
            status: 500,
            message: "InfluxDB error",
            details: error
        }
    }
}

export default { 
    saveToDb
}