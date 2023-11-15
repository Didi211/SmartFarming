import mongoose from "mongoose";

const ruleSchema = new mongoose.Schema({
    name: { 
        type: String,
        required: true,
        trim: true
    },
    sensorId: { 
        type: String,
        required: true
    }, 
    actuatorId: { 
        type: String,
        required: true
    }, 
    expression: { 
        type: String,
        required: true,
        immutable: true,
        enum: { 
            values: ['>', '<', '<=', '>='],
            message: '{VALUE} is not allowed.'
        }
    },
    action: { 
        type: String,
        required: true,
        uppercase: true,
        enum: { 
            values: ['START', 'STOP'],
            message: '{VALUE} is not allowed.'
        },
    },
    triggerLevel: { 
        type: Number,
        required: true 
    }
}, { 
    virtuals: { 
        text: { 
            get: function() { 
                return `${this.action} the actuator when trigger level is ${this.expression} than ${this.triggerLevel}`
            }
           
        }
    },
    toJSON: { 
        virtuals: true
    },
});

const Rule = mongoose.model('Rule', ruleSchema);
export { Rule }