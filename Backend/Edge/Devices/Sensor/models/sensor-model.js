import dbLocal from "db-local";
const { Schema } = new dbLocal({ path: "./databases" });

export const SensorModel = Schema("SensorModel", { 
    _id: String, // sensor name
    sensorName: { type: String, required: true },
    actuatorName: { type: String, default: "" },
    lastReading: { type: Number, default: 50 },
    pumpState: { type: Boolean, default: false}
});