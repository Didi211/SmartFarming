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
    createdAt: { 
        type: Date, 
        default: Date.now
    },
    isRead: { 
        type: Boolean,
        default: false
    }

});

const Notification = mongoose.model('Notification', notificationSchema);
export { Notification };

