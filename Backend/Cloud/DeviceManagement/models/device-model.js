import mongoose from "mongoose";

const deviceSchema = new mongoose.Schema({
    name: { 
        type: String,
        required: true,
        trim: true,
    },
    userId: { 
        type: String,
        required: true
    }, 
    type: { 
        type: String,
        required: true,
        uppercase: true,
        immutable: true,
        enum: { 
            values: ['SENSOR', 'ACTUATOR'],
            message: '{VALUE} is not allowed.'
        }
    },
    status: { 
        type: String,
        required: true,
        uppercase: true,
        enum: { 
            values: ['ONLINE', 'OFFLINE'],
            message: '{VALUE} is not allowed.'
        },
    },
    unit: { 
        type: String,
        default: null,
        required: function() { 
            return this.type == 'SENSOR'
        }, 
        trim: true,
    },
    state: { 
        type: String,
        default: null,
        required: function() {
            return this.type == 'ACTUATOR'
        },
        enum: { 
            values: ['ON','OFF'],
            message: '{VALUE} is not allowed.'
        },
        uppercase: true,
    }
});

deviceSchema.index({ name: 1, userId: 1 }, { unique: true });

deviceSchema.pre('save', function() { 
    // Set unit to null if type is SENSOR
    if (this.type === 'SENSOR') {
        this.state = null;
    }
    
    // Set state to null if type is ACTUATOR
    if (this.type === 'ACTUATOR') {
        this.unit = null;
    }
})

const Device = mongoose.model('Device', deviceSchema);
export { Device }