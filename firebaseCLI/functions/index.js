// const functions = require("firebase-functions");

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//   functions.logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });

// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions      = require('firebase-functions');
const admin          = require('firebase-admin');
const serviceAccount = require('./keyfile.json');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://thewarofstars-8f9a8-default-rtdb.firebaseio.com"
});
const db = admin.firestore();


/**
     *  === 채팅 프로세스 ===
     * 1. 채팅에 필요한 정보를 받아온다. - 성공
     * 2. 게이머의 uID를 통해 fcmToken을 조회한다. - 
     *  2.1. fcmToken이 없다면 함수를 더 진행할 필요없다. 종료한다.
     * 3. RDB에 데이터 쓰기 작업을 진행해준다.
     * 4. 완료했다면 fcmToken을 보내준다
     */
exports.sendMessage = functions.https.onRequest(async (req, res) => {
    
    // 1. 채팅에 필요한 정보를 받아온다.
    const receiverUID  = req.query.to;
    const senderUID    = req.query.from;
    const content      = req.query.content;
    
    // 2. 게이머의 uID가 올바른지 조회한다. fcmToken을 조회한다.
    // 2.1. fcmToken이 없다면 함수를 더 진행할 필요없다. 종료한다.

    // 2.1. fcmToken이 없다면 함수를 더 진행할 필요없다. 종료한다.
    const fcmTokenRef      = db.collection('GamerList').doc(receiverUID);
    const fcmTokenSnapshot = await fcmTokenRef.get();
    const fcmToken         = fcmTokenSnapshot.get('fcmToken');

    // 2.1. fcmToken이 없다면 함수를 더 진행할 필요없다. 종료한다.
    if (fcmToken == null) {
      console.log('fcmToken', '=>', fcmToken);      
      throw new functions.https.HttpsError(20000, 'message : fcmToken is null');
    }

    // 3. RDB에 데이터 쓰기 작업을 진행해준다.
    //  3.1. 말풍선 데이터 한 개를 만든다. 
    //  3.2. 수신자가 이를 참조한다.
    //  3.3. 발신자가 이를 참조한다.

    //  3.1. 말풍선 데이터 한 개를 만든다. 
    const commentRef = admin.database()
    .ref('comments/')
    .push();
    commentRef.set({
      content: content,
      timeStamp: 'helloTimeStamp',
    });
    const commentUID = commentRef.key

    //  3.2. 수신자가 이를 참조한다.
    const receiverRef = admin.database()
    .ref(`user/${receiverUID}/${commentUID}`)
    .set(false);

    //  3.3. 발신자가 이를 참조한다.
    const senderRef = admin.database()
    .ref(`user/${senderUID}/${commentUID}`)
    .set(true);

    // 4. 완료했다면 fcmToken을 보내준다.
    // 4.1. 페이로드를 작성한다.
    // 4.2. 메시지를 전송한다.
    // 4.3. 콜백을 처리한다.

    // 4.1. 페이로드를 작성한다.
    const notiPayload = {
      notification: {
        title: '메시지 도착',
        body: `${content}`,
        icon: 'https://blog.kakaocdn.net/dn/kBexr/btqxjBUVgL6/C1hJKqfcwwfkglSWwQdN91/img.png'
      }
    };

    
    // 4.2. 메시지를 전송한다.
    const response = await admin.messaging()
    .sendToDevice(fcmToken, notiPayload);
    
    
    // 4.3. 콜백을 처리한다.
    response.results.forEach((result, index) => {
      const error = result.error;
      if (error) {

        // 등록이 안된 토큰은 지워준다.
        if (error.code === 'messaging/invalid-registration-token' ||
            error.code === 'messaging/registration-token-not-registered') {
          console.log('noti error', '=>' , error.code);
        }
        
      }
    });

    res.json({result: `to : ${receiverUID}, from : ${senderUID}`});
  });

exports.makeUppercase = functions.firestore.document('/messages/{documentId}')
.onCreate((snap, context) => {
  
  const gamerUID = snap.data().to;
  const from     = snap.data().from;
  const content  = snap.data().content;

  // 선수 이메일을 사용해 fcmToken을 알아낸다
  const fcmToken = admin.firestore.ref('/GamerList/${gamerUID}/fcmToken')
  
  const original = snap.data().original;
  functions.logger.log('Uppercasing', context.params.documentId, original);

  const uppercase = original.toUpperCase();


  return snap.ref.set({uppercase}, {merge: true});
});