import { Point } from "@influxdata/influxdb-client";
import { queryClient, writeClient } from "../config/influxdb-config.js";
import { edgeGatewayAxios } from "../config/axios-config.js";

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

const sendAggregatedData = async () => { 
    let query = 
        `from(bucket: "local-sensor-data")
            |> range(start: -10m)
            |> filter(fn: (r) => r["_measurement"] == "sensor-data")
            |> group(columns: ["host", "sensor-id"], mode: "by")
            |> mean(column: "_value")`;
    let result = [];
    for await (const {values, tableMeta} of queryClient.iterateRows(query)) { 
        const o = tableMeta.toObject(values);
        result.push({
            sensorId: o['sensor-id'],
            reading: o._value
        });
    }
    if (result.length > 0) { 
        edgeGatewayAxios.post('/sync', JSON.stringify({
            data: result
        }));
    }
    return result;
}

export default { 
    saveToDb,
    sendAggregatedData
}