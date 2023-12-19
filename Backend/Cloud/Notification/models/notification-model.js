import mongoose from "mongoose";

const notificationSchema = new mongoose.Schema({ 
    message: { 
        type: String,
        required: true,
        trim: true
    }, 
    userId: { 
        type: String,
        required: true
    },
    deviceId: { 
        type: String,
        required: true
    },
    deviceStatus: { 
        type: String,
        required: true,
        uppercase: true,
        enum: { 
            values: ['ONLINE', 'OFFLINE'],
            message: '{VALUE} is not allowed.'
        },
    },
    isRead: { 
        type: Boolean,
        default: false
    }
}, { 
    timestamps: true
});

const Notification = mongoose.model('Notification', notificationSchema);
export { Notification };

