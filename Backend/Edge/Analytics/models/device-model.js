import mongoose from "mongoose";

const deviceSchema = new mongoose.Schema({
    _id: { type: mongoose.Schema.Types.ObjectId },
    name: { 
        type: String,
        required: true,
        trim: true,
        unique: true
    },
    edgexId: { 
        type: String,
        // required: true,
        unique: true
        
    },
    // userId: { 
    //     type: String,
    //     required: true
    // }, 
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

const Device = mongoose.model('Device', deviceSchema);
export { Device }