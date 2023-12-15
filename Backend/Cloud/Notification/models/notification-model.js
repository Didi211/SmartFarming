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
    isRead: { 
        type: Boolean,
        default: false
    }
}, { 
    timestamps: true
});

const Notification = mongoose.model('Notification', notificationSchema);
export { Notification };

