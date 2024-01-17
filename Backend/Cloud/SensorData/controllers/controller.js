import logic from '../logic/logic.js';
import { handleApiError } from '../utils/error-handler.js';
import responseDtoMapper from '../utils/response-dto-mapper.js';
import pointPeriodsConstants from '../utils/point-periods-constants.js';

const syncData = async (req, res) => { 
    let userId = req.headers['user-id'];
    let data = req.body.data;
    try { 
        let result = await logic.syncData(userId, data);
        let responseDto = responseDtoMapper.succesfullResponseDto(
            200,
            "Syncing data successfull.",
            result
        )
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}

const getHistoryData = async (req, res) => {
    let userId = req.headers['user-id'];
    let sensorId = req.params.id;
    let period = req.query.period;
    let startDate = req.body.startDate;
    let endDate = req.body.endDate;
    try { 
        if (period == undefined) { 
            throw { 
                status: 400,
                message: "Required field 'point period'.",
                details: "Available periods: [hours, months, years]."
            }
        }
        let result;
        switch(period.toUpperCase()) { 
            case pointPeriodsConstants.HOURS: { 
                result = await logic.getHourlyData(userId, sensorId, startDate, endDate);
                break;
            }
            case pointPeriodsConstants.MONTHS: { 
                result = await logic.getMonthlyData(userId, sensorId, startDate, endDate);
                break;
            }
            case pointPeriodsConstants.YEARS: { 
                result = await logic.getYearlyData(userId, sensorId, startDate, endDate);
                break;
            }
            default: { 
                throw { 
                    status: 400,
                    message: "Unknown point period for time validation.",
                    details: "Available periods: [hours, months, years]."
                }
            }
        }
        let responseDto;
        if (result.length > 0) { 
            responseDto = responseDtoMapper.succesfullResponseDto(
                200,
                "Fetching hourly data successfull.",
                result
            )
        }
        else { 
            responseDto = responseDtoMapper.succesfullResponseDto(
                200,
                "No history data for chosen time period.",
                result
            )
        }
        res.status(responseDto.status).send(responseDto);
    }
    catch(error) { 
        handleApiError(res, error);
    }
}


export default { 
    syncData,
    getHistoryData
}