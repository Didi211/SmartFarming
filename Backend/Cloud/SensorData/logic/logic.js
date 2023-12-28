import { writeClient, queryClient } from "../influxdb-config.js";
import { Point } from "@influxdata/influxdb-client";
import timeValidator from "../utils/time-validator.js";
import pointPeriodsConstants from "../utils/point-periods-constants.js";
import dtoMapper from "../utils/dto-mapper.js";

const syncData = async (userId, data) => { 
    // measurement = userId
    // tags = sensorids
    // field = readings
    try { 
        data.forEach(element => {
            let point = new Point(userId)
                .tag('sensor-id',element.sensorId)
                .floatField('reading', element.reading);
            writeClient.writePoint(point);
            writeClient.flush();
        });

        console.log('Synced data:', data);
    }
    catch(error) { 
        throw { 
            status: 500,
            message: "InfluxDB error",
            details: error
        }
    }
}

const getHourlyData = async (userId, sensorId, startDate, endDate) => { 
    return await performCall(userId, sensorId, startDate, endDate, pointPeriodsConstants.HOURS);

}

const getMonthlyData = async (userId, sensorId, startDate, endDate) => { 
    return await performCall(userId, sensorId, startDate, endDate, pointPeriodsConstants.MONTHS);

}

const getYearlyData = async (userId, sensorId, startDate, endDate) => { 
    return await performCall(userId, sensorId, startDate, endDate, pointPeriodsConstants.YEARS);
}

const performCall = async (userId, sensorId, startDate, endDate, pointsPeriod) => { 
    let validationResult = timeValidator.validate(startDate, endDate, pointsPeriod);
    if (validationResult != "") { 
        throw { 
            status: 400,
            message: "Validation failed",
            details: validationResult
        }
    }

    let window = "";
    switch(pointsPeriod) { 
        case pointPeriodsConstants.HOURS: { 
            window = pointPeriodsConstants.ONE_HOUR;
            break;
        }
        case pointPeriodsConstants.MONTHS: { 
            window = pointPeriodsConstants.ONE_MONTH;
            break;
        }
        case pointPeriodsConstants.YEARS: { 
            window = pointPeriodsConstants.ONE_YEAR;
            break;
        }

    }
    try { 
        let query = 
        `from (bucket: "sensor-data")
            |> range(start: ${startDate}, stop: ${endDate})
            |> filter(fn: (r) => r._measurement == "${userId}" and r["sensor-id"] == "${sensorId}")
            |> aggregateWindow(every: ${window}, fn: mean)`;
        let result = [];
        for await (const {values, tableMeta} of queryClient.iterateRows(query)) { 
            const o = tableMeta.toObject(values);
            result.push(o);
        }
        return dtoMapper.toPointsDto(result);
    }
    catch(error) { 
        throw { 
            status: 500,
            message: "InfluxDB error",
            details: error
        }
    }
}


export default { 
    syncData, 
    getHourlyData,
    getMonthlyData,
    getYearlyData
}
