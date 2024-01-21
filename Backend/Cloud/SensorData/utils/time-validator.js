import pointPeriodsConstants from "./point-periods-constants.js";

const validate = (startDate, endDate, pointsPeriod) => { 

    let result = "";
    try { 
        if (startDate == undefined || endDate == undefined) { 
            throw `StartDate and endDate fields are required. Missing fields: [startDate]=${startDate} [endDate]=${endDate}`
        }
        if (Date(startDate) > Date(endDate)) {
            throw `Start date: [${formatDate(startDate)}] is after end date: [${formatDate(endDate)}]`
        }
        switch(pointsPeriod.toUpperCase()) { 
            case pointPeriodsConstants.HOURS: { 
                validateHours(startDate, endDate);
                break;
            }
            case pointPeriodsConstants.MONTHS: { 
                validateMonths(startDate, endDate);
                break;
            }
            case pointPeriodsConstants.YEARS: { 
                validateYears(startDate, endDate);
                break;
            }
            default: { 
                throw "Unknown point period for time validation."
            }
            
        }

    }
    catch(error) { 
        result = error;
    }
    return result;
}

const validateHours = (startDate, endDate) => { 
    startDate = new Date(startDate);
    endDate = new Date(endDate); 

    const maxDays = 10
    let timeDifference = endDate.getTime() - startDate.getTime();
    let hoursDifference = timeDifference / (1000 * 60 * 60);
    if (hoursDifference > 24 * maxDays) {
        throw `Between ${formatDate(startDate)} and ${formatDate(endDate)} has ${hoursDifference} hours. Maximum allowed is ${maxDays * 24} hours.`
    }
}

const validateMonths = (startDate, endDate) => { 
    startDate = new Date(startDate);
    endDate = new Date(endDate); 
    const maxMonths = 10 * 12; // 10 years
    const monthsDifference = (endDate.getFullYear() - startDate.getFullYear()) * 12 + (endDate.getMonth() - startDate.getMonth());
    if (monthsDifference > maxMonths) { 
        throw `Between ${formatDate(startDate)} and ${formatDate(endDate)} has ${monthsDifference} months. Maximum allowed is ${maxMonths} months.` 
    }
}
const validateYears = (startDate, endDate) => { 
    startDate = new Date(startDate);
    endDate = new Date(endDate); 
    const maxYears = 50; 
    const yearsDifference = endDate.getFullYear() - startDate.getFullYear();
    if (yearsDifference > maxYears) { 
        throw `Between ${formatDate(startDate)} and ${formatDate(endDate)} has ${yearsDifference} years. Maximum allowed is ${maxYears} years.` 
    }
}

const formatDate = (date) => {
    date = new Date(date);
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Month is zero-based
    const year = String(date.getFullYear());

    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');

    return `${day}/${month}/${year} - ${hours}:${minutes}`;
}

export default { 
    validate
}