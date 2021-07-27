// const functions = require("firebase-functions");

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//   functions.logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });


// Cloud Function SDK 초기화
const functions = require('firebase-functions');

// Firebase상품들을 통합하기 위해 adminSDK를 초기화
const admin = require('firebase-admin');
admin.initializeApp();

exports.makeUppercase = functions.firestore.document('/messages/{documentId}')
    .onCreate((snap, context) => {
        const original = snap.data().original;

        functions.logger.log('Uppercasing', context.params.documentId, original);

        const uppercase = original.toUpperCase();

        return snap.ref.set({uppercase}, {merge: true});
    })

// exports.addMessage = functions.https.onRequest(async (req, res) => {
//     const original = req.query.text;

//     const writeResult = await admin.firestore().collection('messages').add({original: original});
//     res.json({result: 'Message with ID: ${writeResult.id} added'});
// })

exports.sendMessage = functions.https.onRequest(async (req, res) => {
    const original = req.query.text;

    const to = req.query.to;
    const from = req.query.from;
    const content = req.query.content;
    const currentTime = req.query.currentTime 

    const writeResult1 = await admin.firestore().collection('messages').add({to: to, from: from, content: content});

    res.json({result: 'message send success'});
})