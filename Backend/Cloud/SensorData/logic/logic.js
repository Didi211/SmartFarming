import { writeClient } from "../influxdb-config.js";
import { Point } from "@influxdata/influxdb-client";

const syncData = async (userId, data) => { 
    console.log('syncing data')
    for (let i = 0; i < 5; i++) {
        let point = new Point('measurement1')
          .tag('tagname1', 'tagvalue1')
          .floatField('field2', i*2)
      
        void setTimeout(() => {
          writeClient().writePoint(point)
          console.log(point);
        }, i * 1000) // separate points by 1 second
      
        void setTimeout(() => {
          writeClient().flush();
          console.log('flushed data')
        }, 5000)
    }
    return [];
}

const getHourlyData = async (userId, sensorId, startDate, endDate) => { 

}

export default { 
    syncData, 
    getHourlyData
}