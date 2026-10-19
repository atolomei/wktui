/* wktui MainMenu behaviour */
var wktuiMainMenu = (function () {
	"use strict";

	function init(toggleId, menuId) {
		var toggle = document.getElementById(toggleId);
		var menu = document.getElementById(menuId);
		if (!toggle || !menu) {
			return;
		}

		function positionMenu() {
			// place the menu right below the top bar (the nav that contains the toggle)
			var nav = toggle.closest("nav");
			var top = nav ? nav.getBoundingClientRect().bottom : 0;
			if (top < 0) {
				top = 0;
			}
			if (window.matchMedia("(min-width: 768px)").matches) {
				menu.style.top = top + "px";
			} else {
				menu.style.top = "";
			}
		}

		function isOpen() {
			return menu.classList.contains("open");
		}

		function open() {
			positionMenu();
			menu.classList.add("open");
			document.body.classList.add("main-menu-open");
			toggle.setAttribute("aria-expanded", "true");
		}

		function close() {
			menu.classList.remove("open");
			document.body.classList.remove("main-menu-open");
			toggle.setAttribute("aria-expanded", "false");
		}

		toggle.addEventListener("click", function (e) {
			e.stopPropagation();
			if (isOpen()) {
				close();
			} else {
				open();
			}
		});

		// close when clicking anywhere outside the menu
		document.addEventListener("click", function (e) {
			if (isOpen() && !menu.contains(e.target) && !toggle.contains(e.target)) {
				close();
			}
		});

		// close on Escape
		document.addEventListener("keydown", function (e) {
			if (e.key === "Escape" && isOpen()) {
				close();
			}
		});

		window.addEventListener("resize", positionMenu);
		window.addEventListener("scroll", positionMenu);
	}

	return { init: init };
})();
