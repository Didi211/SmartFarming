import { InfluxDB } from "@influxdata/influxdb-client";

var isConfigured = false;
var client;
const org = process.env.INFLUX_DB_ORGANIZATION;
const bucket = process.env.INFLUX_DB_BUCKET;

const influxDbConfig = () => { 
    const token = process.env.BUCKET_SENSOR_DATA_API_TOKEN;
    const url = process.env.INFLUX_DB_URL

    client = new InfluxDB({url, token});
    isConfigured = true;
    console.log('Connected to InfluxDB');
}

const writeClient = (() => { 
    if (!isConfigured) { 
        influxDbConfig();
    }
    return client.getWriteApi(org,bucket,'ns');
})();

const queryClient = (() => { 
    if (!isConfigured) { 
        influxDbConfig();
    }
    return client.getQueryApi(org);
})();

export {
    writeClient,
    queryClient,
    influxDbConfig
}