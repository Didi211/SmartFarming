const get = async (req, res) => { 
    let name = req.params.name;
}

const ping = async (req, res) => { 
    console.log(req.params);
    console.log(req.body);
    res.status(200).send({
        status:200,
        humidity: 50,
        ping: "ping"
    })
}

const getPing = async (req, res) => { 
    console.log(req);
    res.status(200).send({
        "apiVersion": "v3",
        "requestId": "e6e8a2f4-eb14-4649-9e2b-175247911369",
        "statusCode": 0,
        "message": "string",
        "event": {
          "apiVersion": "v3",
          "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
          "deviceName": "hum-sensor-3",
          "profileName": "Humidity-Sensor",
          "created": 0,
          "origin": 0,
          "readings": [
            "Humidity",
            "54"
          ],
        }
      });
}
export default { 
    get
}