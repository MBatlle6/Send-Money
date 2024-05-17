/**
 * Import function triggers from their respective submodules:
 *
 * const {onCall} = require("firebase-functions/v2/https");
 * const {onDocumentWritten} = require("firebase-functions/v2/firestore");
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

// The Cloud Functions for Firebase SDK to create Cloud Functions and triggers.
// const {logger} = require("firebase-functions");
const {onRequest} = require("firebase-functions/v2/https");
// const {onDocumentCreated} = require("firebase-functions/v2/firestore");

// The Firebase Admin SDK to access Firestore.
const {initializeApp} = require("firebase-admin/app");
const {getFirestore} = require("firebase-admin/firestore");
// Import the Firebase SDK for Google Cloud Functions.
const functions = require("firebase-functions");
// Import and initialize the Firebase Admin SDK.
const admin = require("firebase-admin");
const {onDocumentUpdated} = require("firebase-functions/v2/firestore");

initializeApp();
const db = getFirestore();
const fcm = admin.messaging();

// Take the text parameter passed to this HTTP endpoint and insert it into
// Firestore under the path /messages/:documentId/original
exports.addmessage = onRequest(async (req, res) => {
  // Grab the text parameter.
  const original = req.query.text;
  // Push the new message into Firestore using the Firebase Admin SDK.
  const writeResult = await getFirestore()
      .collection("messages")
      .add({original: original});
    // Send back a message that we've successfully written the message
  res.json({result: `Message with ID: ${writeResult.id} added.`});
});


// Adds a message that welcomes new users into the chat.
exports.addWelcomeMessages = functions.auth.user().onCreate(async (user) => {
  functions.logger.log("A new user signed in for the first time.");
  // const fullName = user.displayName || "Anonymous";

  // Saves the new welcome message into the database
  // which then displays it in the FriendlyChat clients.
  await admin.firestore().collection("messages").add({
    name: "Firebase Bot",
    profilePicUrl: "/images/firebase-logo.png", // Firebase logo
    text: "Signed in for the first time! Welcome!",
    timestamp: admin.firestore.FieldValue.serverTimestamp(),
  });
  functions.logger.log("Welcome message written to database.");
});

exports.sendNotificationLowAmountTokens =
onDocumentUpdated("users/{userId}", async (event) => {
  const tokensAmount = event.data.after.data().tokens;

  if (tokensAmount < 5) {
    const querySnapshot = await db.collection("users").get();

    const notificationPayload = {
      notification: {
        title: "Low Token Balance",
        body: `Your token balance is low. Please add more tokens.`,
      },
    };

    const promises = [];

    querySnapshot.forEach((doc) => {
      const token = doc.data().fcmToken;
      if (token) {
        promises.push(fcm.sendToDevice(token, notificationPayload));
      }
    });

    await Promise.all(promises);
  }
});


// Create and deploy your first functions
// https://firebase.google.com/docs/functions/get-started

// exports.helloWorld = onRequest((request, response) => {
//   logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });
