const toPointDto = (point) => { 
    return { 
        timeMeasured: point._time,
        reading: point._value,
    }
}

const toPointsDto = (points) => { 
    return points.map(x => toPointDto(x));
}

export default { 
    toPointDto,
    toPointsDto
}