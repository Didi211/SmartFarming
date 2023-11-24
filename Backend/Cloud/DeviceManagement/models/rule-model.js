import mongoose from "mongoose";

const ruleSchema = new mongoose.Schema({
    name: { 
        type: String,
        required: true,
        trim: true
    },
    sensorId: { 
        type: String,
        required: true,
        unique: true,
    }, 
    actuatorId: { 
        type: String,
        required: true,
        unique: true,
    }, 
    startExpression: { 
        type: String,
        required: true,
        enum: { 
            values: ['>', '<'],
            message: '{VALUE} is not allowed.'
        }
    },
    stopExpression: { 
        type: String,
        required: true,
        enum: { 
            values: ['>', '<'],
            message: '{VALUE} is not allowed.'
        }
    },
    startTriggerLevel: { 
        type: Number,
        required: true 
    },
    stopTriggerLevel: { 
        type: Number,
        required: true 
    },
}, { 
    virtuals: { 
        text: { 
            get: function() { 
                return `Start the actuator when start trigger level is ${this.startExpression} than ${this.startTriggerLevel}.
                        Stop the actuator when stop trigger level is ${this.stopExpression} than ${this.stopTriggerLevel}`;
            }
           
        }
    },
    toJSON: { 
        virtuals: true
    },
});

const Rule = mongoose.model('Rule', ruleSchema);
export { Rule }