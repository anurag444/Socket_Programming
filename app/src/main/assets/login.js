 (function(startup){
			function start(savedPresentationState)
			{
				var presInfo = "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";
				PresentationPlayer.start(presInfo, "content", "playerView", onPlayerInit, savedPresentationState);
				function onPlayerInit(player)
				{
					(function(player){
	(function(player){
	var lms = iSpring.LMS.instance();
	lms.start(player);

	window.onunload = window.onbeforeunload = function()
	{
		lms.closeLms();
	};
})(player);

})(player);

					var preloader = document.getElementById("preloader");
					preloader.parentNode.removeChild(preloader);
				}
			}

			if (startup)
				startup(start);
			else
				start();
		})((function(start){
	var params = {
		presentationId: "C436CB69-5B4B-4A5A-8C5E-585B2ED98610",
		slidesToView: 4,
		slidesWeight: 1,
		quizzes: [],
		scenarios: [],
		totalScore: 100,
		passingScore: 100,
		timeLimit: 3600,
		exitOnTimeout: false,
		showMessageOnTimeout: false,
		timeoutMessage: "",
		flags: 0,
		resumeMode: "prompt",
		tincan:
		{
			endPoint: "10.0.2.2:781/",
			auth:
			{
				type: "basic",
				key: "",
				login: "0vOt3ULuHH9r2Jw5xBk",
				password: "jsPBrLeq0Pu0DVfjYRY",
				name: "Anurag",
				email: "anurag@gmail.com"
			},
			i18n:{
    "TINCAN_AUTH_AUTHENTICATION_ERROR_WINDOW_TEXT" : "Authentication failed",
    "TINCAN_AUTH_CONNECTION_ERROR_WINDOW_TEXT" : "Cannot connect to Learning Record Store (LRS)\nPlease check your internet connection and try again.",
    "TINCAN_AUTH_CONNECTION_ERROR_WINDOW_TITLE" : "Connection Problem",
    "TINCAN_AUTH_ERROR_WINDOW_CLOSE_BUTTON_CAPTION" : "Close",
    "TINCAN_AUTH_ERROR_WINDOW_RETRY_BUTTON_CAPTION" : "Try Again",
    "TINCAN_AUTH_ERROR_WINDOW_TITLE" : "Error",
    "TINCAN_AUTH_INVALID_EMAIL_WINDOW_BUTTON_CAPTION" : "OK",
    "TINCAN_AUTH_INVALID_EMAIL_WINDOW_TEXT" : "Please enter a valid email address.",
    "TINCAN_AUTH_LOGIN_WINDOW_BUTTON_CAPTION" : "Login",
    "TINCAN_AUTH_LOGIN_WINDOW_LOGIN_FIELD_CAPTION" : "Login:",
    "TINCAN_AUTH_LOGIN_WINDOW_PASSWORD_FIELD_CAPTION" : "Password:",
    "TINCAN_AUTH_USER_INFO_WINDOW_BUTTON_CAPTION" : "OK",
    "TINCAN_AUTH_USER_INFO_WINDOW_EMAIL_CAPTION" : "Email:",
    "TINCAN_AUTH_USER_INFO_WINDOW_NAME_CAPTION" : "Name:",
    "TINCAN_AUTH_WINDOW_SUBTITLE" : "Introduce yourself",
    "TINCAN_AUTH_WINDOW_TITLE" : "Learning Record Store"
}
		},
		scorm2004:
		{
			edition: ""
		}
	};

	var lms = iSpring.LMS.create("ISPRING_LMS_ID", "tincan", params);
	lms.initialize(start);
})
);