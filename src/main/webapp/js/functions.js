function startAttack(endpoint) {
	$.post(endpoint, $("#attack-form").serialize()).done(function(data) {

		// clear any results from a previous attack
		$("#attack-results li").remove();

		$("#attack-results-wrapper").show();
		var attackResults = $("#attack-results");

		var poll = function() {
			$.ajax({
				url : endpoint,
				dataType : 'json',
				type : 'get',
				success : function(data) {
					console.log(data);
					if (data == "complete") {
						clearInterval(pollInterval);
					} else {
						// provide a visual cue that something has changed
						$("#attack-results-wrapper").animate({
							"border-color" : "#C0C0C0"
						}, 400, function() {
							$("#attack-results-wrapper").animate({
								"border-color" : "#2F4F4F"
							}, 200);
						});
						
						$.each(data, function(index, data) {
							$("<li>").text(data).appendTo(attackResults);
						});
					}
				},
				error : function() {
					console.log('Error!');
				}
			})
		};

		var delay = $("#delay").val();
		delay = (delay == "0") ? 2000 : delay * 1000;
		var pollInterval = setInterval(function() {
			poll();
		}, delay);
	}); // $.post
}