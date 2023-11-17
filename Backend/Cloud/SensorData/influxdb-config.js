import { InfluxDB } from "@influxdata/influxdb-client";

var isConfigured = false;
var client;
const org = process.env.INFLUX_DB_ORGANIZATION;
const bucket = process.env.INFLUX_DB_BUCKET;
console.log({ 
    org: org, 
    bucket: bucket
});

const influxDbConfig = () => { 
    const token = process.env.BUCKET_SENSOR_DATA_API_TOKEN;
    const url = process.env.INFLUX_DB_URL
    console.log({
        token: token,
        url: url
    });

    client = new InfluxDB({url, token});
    isConfigured = true;
    console.log('configured influx db')
}

const writeClient = () => { 
    if (!isConfigured) { 
        console.log('calling influx config from writeClient()');
        influxDbConfig();
    }
    return client.getWriteApi(org,bucket,'ns');
}
const queryClient = () => { 
    if (!isConfigured) { 
        console.log('calling influx config from queryClient()');
        influxDbConfig();
    }
    return client.getQueryApi(org);
}

export {
    writeClient,
    queryClient,
    influxDbConfig
}