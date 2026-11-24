// Baby Steps Retro Pixel Art & Tamagotchi App Engine
(function () {
  "use strict";

  // State Management
  const state = {
    babyName: "Sara",
    gender: "girl", // 'girl' | 'boy' | 'surprise'
    weeks: 26,
    startDate: "2026-05-28",
    dueDate: "2027-03-04",
    dateMode: true,
    sleepMode: "auto", // 'auto' | 'sleep' | 'awake'
    isMusicActive: false,
    milestones: []
  };

  // Sprite Matrices (18x13)
  const crawl1 = [
    ".........booby....",
    ".........ybbssy...",
    "........yssssssy..",
    ".......yssskwssy..",
    ".......yssskkssss.",
    ".......ysccsssss..",
    ".......ssssssmss..",
    "...wwwwwwsssss....",
    "..wwwwwwwwssss....",
    ".wwuwwwwww.ss.ee..",
    ".wdddddddw.ss.ee..",
    "..sssssddw.ss.ee..",
    "...ss...ss.ss.ss.."
  ];

  const crawl2 = [
    ".........booby....",
    ".........ybbssy...",
    "........yssssssy..",
    ".......yssskwssy..",
    ".......yssskkssss.",
    ".......ysccsssss..",
    ".......ssssssmss..",
    "...wwwwwwsssss....",
    "..wwwwwwwwssss....",
    ".wwuwwwwww.ssss...",
    ".wdddddddw..sss...",
    "..sssssddw..sss...",
    "...ssss.....sss..."
  ];

  const crawl3 = [
    ".........booby....",
    ".........ybbssy...",
    "........yssssssy..",
    ".......yssskwssy..",
    ".......yssskkssss.",
    ".......ysccsssss..",
    ".......ssssssmss..",
    "...wwwwwwsssss....",
    "..wwwwwwwwssss....",
    ".wwuwwwwww..ssss..",
    ".wdddddddw..ssss..",
    "..sssssddw.ee.ss..",
    "..ssss..ss.ee.ss.."
  ];

  const crawl4 = [
    ".........booby....",
    ".........ybbssy...",
    "........yssssssy..",
    ".......yssskwssy..",
    ".......yssskkssss.",
    ".......ysccsssss..",
    ".......ssssssmss..",
    "...wwwwwwsssss....",
    "..wwwwwwwwssss....",
    ".wwuwwwwww.ssss...",
    ".wdddddddw..sss...",
    "..sssssddw..sss...",
    "....ssss....sss..."
  ];

  // Music Dance Sprites (18x13)
  const musicCrawl1 = [
    ".........booby....",
    ".........ybbssy.s.",
    "........yssssssys.",
    ".......yssskwssy..",
    ".......yssskkssss.",
    ".......ysccsssss..",
    ".......ssssssmss..",
    "...wwwwwwsssss....",
    "..wwwwwwwwssss....",
    ".wwuwwwwww.ss.....",
    ".wdddddddw.ss.....",
    "..sssssddw.ss.....",
    "...ss...ss.ss....."
  ];

  const musicCrawl2 = [
    ".........booby....",
    ".........ybbssy...",
    "........yssssssy..",
    ".......yssskwssy..",
    ".......yssskkssss.",
    ".......ysccsssss..",
    ".......ssssssmmm..",
    "...wwwwwwssssss...",
    "..wwwwwwwwssss....",
    ".wwuwwwwww.ssss...",
    ".wdddddddw..sss...",
    "..sssssddw..sss...",
    "...ssss.....sss..."
  ];

  const musicCrawl3 = [
    ".........booby....",
    ".......ssybbssy...",
    ".......ssyssssssy.",
    ".......yssskwssy..",
    ".......yssskkssss.",
    ".......ysccsssss..",
    ".......ssssssmss..",
    "...wwwwwwsssss....",
    "..wwwwwwwwssss....",
    ".wwuwwwwww..ssss..",
    ".wdddddddw..ssss..",
    "..sssssddw.ee.ss..",
    "..ssss..ss.ee.ss.."
  ];

  const musicCrawl4 = [
    ".........booby....",
    ".........ybbssy...",
    "........yssssssy..",
    ".......yssskwssy..",
    ".......yssskkssss.",
    ".......ysccsssss..",
    ".......ssssssmmm..",
    "...wwwwwwssssss...",
    "..wwwwwwwwssss....",
    ".wwuwwwwww.ssss...",
    ".wdddddddw..sss...",
    "..sssssddw..sss...",
    "....ssss....sss..."
  ];

  // Sleep Sprites (18x13)
  const sleep1 = [
    ".........booby....",
    ".........ybbssy...",
    "........yssssssy..",
    ".......ysssssssy..",
    ".......yssskkkss..",
    ".......ysccsssss..",
    ".......ssssssmss..",
    "..wwwwwwwsssss....",
    ".wwwwwwwwwssss....",
    ".wwuwwwwww.sssss..",
    ".wdddddddw..sssss.",
    "..sssssddw...sss..",
    "...sssssss........"
  ];

  const sleep2 = [
    ".........booby....",
    ".........ybbssy...",
    "........yssssssy..",
    ".......ysssssssy..",
    ".......yssskkkss..",
    ".......ysccsssss..",
    ".......ssssssmss..",
    "...wwwwwwsssss....",
    "..wwwwwwwwssss....",
    ".wwuwwwwww.sssss..",
    ".wdddddddw..sssss.",
    "..sssssddw...sss..",
    "...sssssss........"
  ];

  // Additional 8-bit Elements
  const sprMoon = [
    "...yyyy",
    "..yy...",
    ".yy....",
    ".yy....",
    ".yy....",
    "..yy...",
    "...yyyy"
  ];

  const crib = [
    ".......rr.........",
    "......rrrr........",
    ".......rr.........",
    ".......mm.........",
    "...yyyyyyyyyyy....",
    "..y...........y...",
    "..y.wwwwwwwww.y...",
    "..y.wwwwwwwww.y...",
    "..y.m.m.m.m.m.y...",
    "..y.m.m.m.m.m.y...",
    "..y.m.m.m.m.m.y...",
    "..yyyyyyyyyyyyy...",
    "..y...........y...",
    "..y...........y...",
    "..y...........y..."
  ];

  const noteA = [
    "...p.",
    "...p.",
    "...p.",
    ".ppp.",
    ".ppp."
  ];

  const noteB = [
    "..ppppp",
    "..p...p",
    "..p...p",
    "ppp.ppp",
    "ppp.ppp"
  ];

  const sprZ = [
    "zzzzz",
    "...z.",
    "..z..",
    ".z...",
    "zzzzz"
  ];

  const sprSmallZ = [
    "zzzz",
    "..z.",
    ".z..",
    "zzzz"
  ];

  // Dynamic Palettes
  function getPalette() {
    const isGirl = state.gender === "girl";
    const isBoy = state.gender === "boy";

    return {
      k: "#281919",
      w: "#ffffff",
      s: "#fedbc0",
      e: "#e8b696",
      c: isGirl ? "#fb7185" : "#ff90a2",
      m: isGirl ? "#f472b6" : "#38bdf8",
      d: isGirl ? "#e9a9cb" : "#93c5fd",
      y: "#f5b041",
      b: isGirl ? "#f472b6" : "#f5b041",
      o: isGirl ? "#fda4af" : "#f5b041",
      u: isGirl ? "#f472b6" : isBoy ? "#60a5fa" : "#fbbf24",
      p: "#f43f5e",
      r: isGirl ? "#fb7185" : "#ef4444",
      z: "#818cf8",
      g: "#22c55e"
    };
  }

  // Draw 2D Pixel Sprite Matrix
  function drawSprite(ctx, matrix, x, y, scale, pal) {
    for (let r = 0; r < matrix.length; r++) {
      const row = matrix[r];
      for (let c = 0; c < row.length; c++) {
        const char = row[c];
        if (char !== "." && pal[char]) {
          ctx.fillStyle = pal[char];
          ctx.fillRect(
            Math.round(x + c * scale),
            Math.round(y + r * scale),
            Math.ceil(scale),
            Math.ceil(scale)
          );
        }
      }
    }
  }

  // Bridge Helpers
  function callHaptic() {
    if (window.AndroidBridge && typeof window.AndroidBridge.haptic === "function") {
      window.AndroidBridge.haptic();
    }
  }

  function savePref(k, v) {
    if (window.AndroidBridge && typeof window.AndroidBridge.savePreference === "function") {
      window.AndroidBridge.savePreference(k, String(v));
    } else {
      localStorage.setItem("babysteps_" + k, String(v));
    }
  }

  function getPref(k, def) {
    if (window.AndroidBridge && typeof window.AndroidBridge.getPreference === "function") {
      return window.AndroidBridge.getPreference(k, String(def));
    }
    const val = localStorage.getItem("babysteps_" + k);
    return val !== null ? val : def;
  }

  function saveWeeksBridge(w) {
    if (window.AndroidBridge && typeof window.AndroidBridge.saveWeeks === "function") {
      window.AndroidBridge.saveWeeks(w);
    }
  }

  // Calculation Logic
  function calculateDates() {
    const now = new Date();
    if (state.dateMode && state.dueDate) {
      const due = new Date(state.dueDate + "T00:00:00");
      if (!isNaN(due.getTime())) {
        const diffMs = due.getTime() - now.getTime();
        const diffDays = Math.ceil(diffMs / (1000 * 60 * 60 * 24));
        const daysLeft = Math.max(0, diffDays);
        const weeksLeft = Math.max(0, Math.floor(daysLeft / 7));
        const computedWeeks = Math.max(1, Math.min(40, 40 - weeksLeft));
        state.weeks = computedWeeks;
      }
    }
  }

  function getProgressInfo() {
    const weeks = Math.max(1, Math.min(40, state.weeks));
    const now = new Date();
    let daysLeft = 100;
    let weeksLeft = 14;

    if (state.dueDate) {
      const due = new Date(state.dueDate + "T00:00:00");
      if (!isNaN(due.getTime())) {
        const diffMs = due.getTime() - now.getTime();
        daysLeft = Math.max(0, Math.ceil(diffMs / (1000 * 60 * 60 * 24)));
        weeksLeft = Math.max(0, Math.floor(daysLeft / 7));
      }
    } else {
      weeksLeft = Math.max(0, 40 - weeks);
      daysLeft = weeksLeft * 7;
    }

    const progressPct = Math.max(0, Math.min(100, Math.round((weeks / 40) * 100)));
    const trimester = weeks <= 12 ? "1ST TRI" : weeks <= 26 ? "2ND TRI" : "3RD TRI";

    return { weeks, daysLeft, weeksLeft, progressPct, trimester };
  }

  function isNightTime() {
    if (state.sleepMode === "sleep") return true;
    if (state.sleepMode === "awake") return false;
    const hour = new Date().getHours();
    return hour < 7 || hour >= 20;
  }

  // Render Track Canvas (Top Mini-Game)
  function renderTrack(ctx, width, height, time) {
    ctx.clearRect(0, 0, width, height);
    const pal = getPalette();
    const info = getProgressInfo();
    const isNight = isNightTime();

    const groundY = height - 24;
    const trackStartX = 16;
    const cribScale = 2.4;
    const cribW = 18 * cribScale;
    const trackEndX = width - cribW - 24;
    const trackLen = Math.max(20, trackEndX - trackStartX);

    // Starry night sky
    if (isNight) {
      ctx.fillStyle = "rgba(9, 13, 22, 0.65)";
      ctx.fillRect(0, 0, width, groundY + 12);
      drawSprite(ctx, sprMoon, trackEndX - 30, 8, 1.8, pal);

      ctx.fillStyle = "rgba(253, 224, 71, 0.85)";
      if (Math.floor(time * 2) % 3 !== 0) ctx.fillRect(trackStartX + trackLen * 0.15, 14, 3, 3);
      if ((Math.floor(time * 2) + 1) % 3 !== 0) ctx.fillRect(trackStartX + trackLen * 0.42, 10, 3, 3);
      if ((Math.floor(time * 2) + 2) % 3 !== 0) ctx.fillRect(trackStartX + trackLen * 0.72, 18, 3, 3);
    }

    // Checkered Track
    const dotsCount = Math.floor(trackLen / 12);
    for (let i = 0; i < dotsCount; i++) {
      const dotX = trackStartX + i * 12;
      ctx.fillStyle = i % 2 === 0 ? "rgba(244, 114, 182, 0.5)" : "rgba(255, 255, 255, 0.2)";
      ctx.fillRect(dotX, groundY + 6, 6, 3);
    }

    // Milestones markers
    ctx.fillStyle = "rgba(255, 255, 255, 0.4)";
    ctx.fillRect(trackStartX + trackLen * 0.25, groundY, 2, 8);
    ctx.fillStyle = "rgba(244, 114, 182, 0.8)";
    ctx.fillRect(trackStartX + trackLen * 0.5, groundY - 4, 3, 12);
    ctx.fillStyle = "rgba(255, 255, 255, 0.4)";
    ctx.fillRect(trackStartX + trackLen * 0.75, groundY, 2, 8);

    // Crib at the end
    const cribX = width - cribW - 14;
    const cribY = groundY - 15 * cribScale + 6;
    drawSprite(ctx, crib, cribX, cribY, cribScale, pal);

    // Floating heart above crib
    const heartBob = Math.sin(time * 3) * 3;
    ctx.fillStyle = state.gender === "girl" ? "#fb7185" : "#ef4444";
    ctx.fillRect(cribX + 7 * cribScale, cribY - 8 + heartBob, 5, 5);

    // Baby Position & Animation
    const babyPct = info.progressPct / 100;
    const babyX = trackStartX + babyPct * (trackLen - 30);
    const scaleFactor = info.weeks <= 12 ? 2.0 : info.weeks <= 26 ? 2.4 : info.weeks <= 36 ? 2.7 : 3.0;

    const isDancing = state.isMusicActive;
    const effectiveSleeping = isNight;
    const danceFrame = Math.floor(time * 6.0) % 4;
    const crawlFrame = Math.floor(time * 3.5) % 4;
    const sleepFrame = Math.floor(time * 1.5) % 2;

    const bob = isDancing
      ? Math.sin(time * 12) * 4
      : effectiveSleeping
      ? Math.sin(time * 2) * 1.5
      : Math.sin(time * 7) * 2;
    const babyY = groundY - 13 * scaleFactor + 6 + bob;

    let spr = isDancing
      ? [musicCrawl1, musicCrawl2, musicCrawl3, musicCrawl4][danceFrame]
      : effectiveSleeping
      ? [sleep1, sleep2][sleepFrame]
      : [crawl1, crawl2, crawl3, crawl4][crawlFrame];

    drawSprite(ctx, spr, babyX, babyY, scaleFactor, pal);

    // Floating Zzz or Musical Notes
    if (effectiveSleeping) {
      const z1X = babyX + 16 * scaleFactor + Math.sin(time * 2.5) * 4;
      const z1Y = groundY - 14 * scaleFactor - ((time * 12) % 24);
      drawSprite(ctx, sprSmallZ, z1X, z1Y, 1.4, pal);
    } else if (isDancing) {
      const n1X = babyX + 16 * scaleFactor + Math.sin(time * 3) * 6;
      const n1Y = groundY - 16 * scaleFactor - ((time * 20) % 30);
      drawSprite(ctx, noteA, n1X, n1Y, 1.8, pal);
    }
  }

  // Render Nursery Tamagotchi Canvas
  function renderNursery(ctx, width, height, time) {
    ctx.clearRect(0, 0, width, height);
    const pal = getPalette();
    const info = getProgressInfo();
    const isNight = isNightTime();

    // Wallpaper stripes
    const stripeCount = Math.floor(width / 24);
    for (let i = 0; i < stripeCount; i++) {
      ctx.fillStyle = state.gender === "girl" ? "rgba(244, 114, 182, 0.05)" : "rgba(96, 165, 250, 0.05)";
      ctx.fillRect(i * 24, 0, 12, height);
    }

    // Night overlay
    if (isNight) {
      ctx.fillStyle = "rgba(7, 10, 19, 0.55)";
      ctx.fillRect(0, 0, width, height);
      drawSprite(ctx, sprMoon, width - 60, 20, 2.5, pal);
      ctx.fillStyle = "rgba(253, 224, 71, 0.85)";
      ctx.fillRect(width - 95, 30, 4, 4);
      ctx.fillRect(width - 125, 45, 3, 3);
    }

    // Floor line
    const floorY = height - 44;
    ctx.fillStyle = "rgba(30, 41, 59, 0.6)";
    ctx.fillRect(0, floorY, width, height - floorY);
    ctx.fillStyle = "rgba(255, 255, 255, 0.12)";
    ctx.fillRect(0, floorY, width, 2);

    // Big Nursery Crib
    const bigCribScale = 3.6;
    const nCribX = width - 18 * bigCribScale - 20;
    const nCribY = floorY - 15 * bigCribScale + 6;
    drawSprite(ctx, crib, nCribX, nCribY, bigCribScale, pal);

    // Baby in Nursery
    const nScale = info.weeks <= 12 ? 4.0 : info.weeks <= 26 ? 5.0 : info.weeks <= 36 ? 5.8 : 6.5;
    const isDancing = state.isMusicActive;
    const effectiveSleeping = isNight;
    const nAnimFrame = isDancing ? Math.floor(time * 6.0) % 4 : Math.floor(time * 3.0) % 4;
    const nSleepFrame = Math.floor(time * 1.5) % 2;

    const nBob = isDancing
      ? Math.sin(time * 12) * 6
      : effectiveSleeping
      ? Math.sin(time * 2) * 2
      : Math.sin(time * 6) * 3;
    const nBabyX = 40 + (info.progressPct / 100) * (width - 240);
    const nBabyY = floorY - 13 * nScale + 8 + nBob;

    const nSpr = isDancing
      ? [musicCrawl1, musicCrawl2, musicCrawl3, musicCrawl4][nAnimFrame]
      : effectiveSleeping
      ? [sleep1, sleep2][nSleepFrame]
      : [crawl1, crawl2, crawl3, crawl4][nAnimFrame];

    drawSprite(ctx, nSpr, nBabyX, nBabyY, nScale, pal);

    // Large floating Zzz / Notes
    if (effectiveSleeping) {
      const z1X = nBabyX + 22 * nScale + Math.sin(time * 2) * 10;
      const z1Y = floorY - 20 * nScale - ((time * 18) % 45);
      drawSprite(ctx, sprSmallZ, z1X, z1Y, 2.2, pal);

      const z2X = nBabyX + 30 * nScale + Math.sin(time * 2.5 + 1) * 12;
      const z2Y = floorY - 26 * nScale - (((time * 16) + 14) % 52);
      drawSprite(ctx, sprZ, z2X, z2Y, 2.6, pal);
    } else if (isDancing) {
      const n1X = nBabyX + 20 * nScale + Math.sin(time * 3) * 12;
      const n1Y = floorY - 24 * nScale - ((time * 28) % 55);
      drawSprite(ctx, noteA, n1X, n1Y, 2.8, pal);

      const n2X = nBabyX - 10 + Math.sin(time * 4 + 1) * 10;
      const n2Y = floorY - 20 * nScale - (((time * 24) + 20) % 60);
      drawSprite(ctx, noteB, n2X, n2Y, 2.6, pal);
    }

    // Balloon above baby
    const balloonX = nBabyX + 24;
    const balloonY = 28 + Math.sin(time * 4) * 4;
    const icon = state.gender === "girl" ? "🎀" : state.gender === "boy" ? "🧢" : "💛";
    const balloonText = isDancing ? "🎶 ♫ ♪" : effectiveSleeping ? "💤 Zzz..." : `${icon} ${state.babyName || "Sara"}`;
    const balloonW = Math.max(60, balloonText.length * 10 + 20);

    ctx.fillStyle = "rgba(15, 23, 42, 0.9)";
    ctx.strokeStyle = "rgba(255, 255, 255, 0.2)";
    ctx.lineWidth = 1.5;
    ctx.beginPath();
    ctx.roundRect(balloonX - 10, balloonY - 8, balloonW, 26, 8);
    ctx.fill();
    ctx.stroke();

    ctx.fillStyle = "#ffffff";
    ctx.font = "bold 13px " + getComputedStyle(document.body).fontFamily;
    ctx.textAlign = "center";
    ctx.textBaseline = "middle";
    ctx.fillText(balloonText, balloonX - 10 + balloonW / 2, balloonY + 5);
  }

  // Update DOM Elements
  function updateUI() {
    const info = getProgressInfo();
    const icon = state.gender === "girl" ? "🎀" : state.gender === "boy" ? "🧢" : "💛";

    // Header Badges
    document.getElementById("badgeWeek").textContent = `${icon} WK ${info.weeks}`;
    document.getElementById("badgeTrimester").textContent = info.trimester;
    document.getElementById("badgeName").textContent = `${icon} ${(state.babyName || "Sara").toUpperCase()}`;

    // Milestone details
    let milestone = {
      fruit: "Lettuce",
      emoji: "🍈",
      length: "35.6 cm",
      weight: "760 g",
      fact: "Baby is developing rapid eye movement (REM) sleep, and hearing grows sharper!"
    };

    if (state.milestones && state.milestones.length > 0) {
      const idx = Math.max(0, Math.min(state.milestones.length - 1, info.weeks - 1));
      if (state.milestones[idx]) milestone = state.milestones[idx];
    }

    // Footer
    document.getElementById("footerFruit").textContent = `${icon} ${state.babyName || "Sara"} • ${milestone.fruit} (${milestone.length})`;
    document.getElementById("footerDays").textContent = `${info.daysLeft}d left (${info.weeksLeft}w)`;
    document.getElementById("footerPct").textContent = `• ${info.progressPct}%`;

    // Milestones Card
    document.getElementById("milestoneEmoji").textContent = milestone.emoji || "🍈";
    document.getElementById("milestoneFruit").textContent = `SIZE: ${(milestone.fruit || "Lettuce").toUpperCase()}`;
    document.getElementById("milestoneSize").textContent = `Length: ~${milestone.length} • Weight: ~${milestone.weight || "760 g"}`;
    document.getElementById("milestoneFact").textContent = milestone.fact || "Baby grows and prepares for the world each day!";

    // Status Banners
    const sleepBanner = document.getElementById("sleepBanner");
    const musicBanner = document.getElementById("musicBanner");
    const isNight = isNightTime();

    if (isNight) {
      sleepBanner.style.display = "flex";
      document.getElementById("sleepBannerTitle").textContent = `${(state.babyName || "Sara").toUpperCase()} IS SLEEPING SOUNDLY`;
    } else {
      sleepBanner.style.display = "none";
    }

    if (state.isMusicActive) {
      musicBanner.style.display = "flex";
      document.getElementById("musicBannerTitle").textContent = `${(state.babyName || "Sara").toUpperCase()} IS DANCING TO THE BEAT!`;
    } else {
      musicBanner.style.display = "none";
    }

    // Settings
    document.getElementById("inputBabyName").value = state.babyName;
    document.getElementById("labelGenderDisplay").textContent =
      state.gender === "girl" ? "It's a Girl! 🎀" : state.gender === "boy" ? "It's a Boy! 🧢" : "It's a Surprise! 💛";

    // Gender buttons
    document.getElementById("btnGenderGirl").classList.toggle("active", state.gender === "girl");
    document.getElementById("btnGenderBoy").classList.toggle("active", state.gender === "boy");
    document.getElementById("btnGenderSurprise").classList.toggle("active", state.gender === "surprise");

    // Sleep mode buttons
    document.getElementById("btnSleepAuto").classList.toggle("active", state.sleepMode === "auto");
    document.getElementById("btnSleepForce").classList.toggle("active", state.sleepMode === "sleep");
    document.getElementById("btnSleepAwake").classList.toggle("active", state.sleepMode === "awake");
    document.getElementById("chipSleepState").textContent = isNight ? "🌙 SLEEPING" : "☀️ AWAKE";

    // Slider
    document.getElementById("weekSlider").value = info.weeks;
    document.getElementById("lblSliderWeek").textContent = `WEEK ${info.weeks}`;

    // Dates
    document.getElementById("inputStartDate").value = state.startDate;
    document.getElementById("inputDueDate").value = state.dueDate;
    document.getElementById("lblStartDate").textContent = state.startDate || "—";
    document.getElementById("lblDueDate").textContent = state.dueDate || "—";

    document.getElementById("dateSummaryText").textContent =
      `Calculated: ${info.weeks} weeks pregnant (${info.daysLeft}d left)`;
    document.getElementById("dateSummaryPct").textContent = `${info.progressPct}%`;
  }

  // Load Milestones JSON
  async function loadMilestones() {
    try {
      const resp = await fetch("milestones.json");
      if (resp.ok) {
        state.milestones = await resp.json();
      }
    } catch (e) {
      console.warn("Could not load milestones.json directly, fallback available", e);
    }
  }

  // Setup Event Handlers
  function setupEvents() {
    // Steppers
    document.getElementById("btnMinus").addEventListener("click", () => {
      callHaptic();
      state.weeks = Math.max(1, state.weeks - 1);
      state.dateMode = false;
      savePref("weeks", state.weeks);
      saveWeeksBridge(state.weeks);
      updateUI();
    });

    document.getElementById("btnPlus").addEventListener("click", () => {
      callHaptic();
      state.weeks = Math.min(40, state.weeks + 1);
      state.dateMode = false;
      savePref("weeks", state.weeks);
      saveWeeksBridge(state.weeks);
      updateUI();
    });

    // Baby Name Input
    document.getElementById("inputBabyName").addEventListener("input", (e) => {
      state.babyName = e.target.value.trim();
      savePref("babyName", state.babyName);
      updateUI();
    });

    // Gender buttons
    document.getElementById("btnGenderGirl").addEventListener("click", () => {
      callHaptic();
      state.gender = "girl";
      savePref("gender", state.gender);
      updateUI();
    });

    document.getElementById("btnGenderBoy").addEventListener("click", () => {
      callHaptic();
      state.gender = "boy";
      savePref("gender", state.gender);
      updateUI();
    });

    document.getElementById("btnGenderSurprise").addEventListener("click", () => {
      callHaptic();
      state.gender = "surprise";
      savePref("gender", state.gender);
      updateUI();
    });

    // Sleep mode buttons
    document.getElementById("btnSleepAuto").addEventListener("click", () => {
      callHaptic();
      state.sleepMode = "auto";
      updateUI();
    });

    document.getElementById("btnSleepForce").addEventListener("click", () => {
      callHaptic();
      state.sleepMode = "sleep";
      updateUI();
    });

    document.getElementById("btnSleepAwake").addEventListener("click", () => {
      callHaptic();
      state.sleepMode = "awake";
      updateUI();
    });

    document.getElementById("btnWakeUp").addEventListener("click", () => {
      callHaptic();
      state.sleepMode = "awake";
      updateUI();
    });

    // Music buttons
    const toggleMusic = () => {
      callHaptic();
      state.isMusicActive = !state.isMusicActive;
      document.getElementById("btnMusicPreview").textContent = state.isMusicActive ? "🎶 Stop Beat" : "🎵 Preview Beat";
      document.getElementById("btnToggleDance").textContent = state.isMusicActive ? "Dance: ON" : "Dance: OFF";
      updateUI();
    };

    document.getElementById("btnMusicPreview").addEventListener("click", toggleMusic);
    document.getElementById("btnToggleDance").addEventListener("click", toggleMusic);

    // Slider
    document.getElementById("weekSlider").addEventListener("input", (e) => {
      state.weeks = parseInt(e.target.value, 10);
      state.dateMode = false;
      savePref("weeks", state.weeks);
      saveWeeksBridge(state.weeks);
      updateUI();
    });

    // Preset Jumps
    document.getElementById("jumpTri1").addEventListener("click", () => {
      callHaptic();
      state.weeks = 8;
      state.dateMode = false;
      savePref("weeks", state.weeks);
      saveWeeksBridge(state.weeks);
      updateUI();
    });

    document.getElementById("jumpTri2").addEventListener("click", () => {
      callHaptic();
      state.weeks = 20;
      state.dateMode = false;
      savePref("weeks", state.weeks);
      saveWeeksBridge(state.weeks);
      updateUI();
    });

    document.getElementById("jumpTri3").addEventListener("click", () => {
      callHaptic();
      state.weeks = 32;
      state.dateMode = false;
      savePref("weeks", state.weeks);
      saveWeeksBridge(state.weeks);
      updateUI();
    });

    document.getElementById("jumpDue").addEventListener("click", () => {
      callHaptic();
      state.weeks = 40;
      state.dateMode = false;
      savePref("weeks", state.weeks);
      saveWeeksBridge(state.weeks);
      updateUI();
    });

    // Date inputs
    document.getElementById("inputStartDate").addEventListener("change", (e) => {
      state.startDate = e.target.value;
      savePref("startDate", state.startDate);
      // Automatically project 40 weeks to due date if empty
      if (state.startDate && !state.dueDate) {
        const d = new Date(state.startDate + "T00:00:00");
        d.setDate(d.getDate() + 280);
        state.dueDate = d.toISOString().split("T")[0];
        savePref("dueDate", state.dueDate);
      }
      state.dateMode = true;
      calculateDates();
      saveWeeksBridge(state.weeks);
      updateUI();
    });

    document.getElementById("inputDueDate").addEventListener("change", (e) => {
      state.dueDate = e.target.value;
      savePref("dueDate", state.dueDate);
      state.dateMode = true;
      calculateDates();
      saveWeeksBridge(state.weeks);
      updateUI();
    });

    // Date Presets
    document.getElementById("presetStartToday").addEventListener("click", () => {
      callHaptic();
      state.startDate = new Date().toISOString().split("T")[0];
      savePref("startDate", state.startDate);
      updateUI();
    });

    document.getElementById("presetDue40wFromStart").addEventListener("click", () => {
      callHaptic();
      if (state.startDate) {
        const d = new Date(state.startDate + "T00:00:00");
        d.setDate(d.getDate() + 280);
        state.dueDate = d.toISOString().split("T")[0];
        savePref("dueDate", state.dueDate);
        state.dateMode = true;
        calculateDates();
        saveWeeksBridge(state.weeks);
        updateUI();
      }
    });

    document.getElementById("presetDueClear").addEventListener("click", () => {
      callHaptic();
      state.dueDate = "";
      savePref("dueDate", "");
      updateUI();
    });
  }

  // Restore State from Preferences
  function restoreState() {
    state.babyName = getPref("babyName", "Sara");
    state.gender = getPref("gender", "girl");
    const savedWeeks = parseInt(getPref("weeks", "26"), 10);
    if (!isNaN(savedWeeks)) state.weeks = savedWeeks;
    state.dueDate = getPref("dueDate", "2027-03-04");
    state.startDate = getPref("startDate", "2026-05-28");
    calculateDates();
  }

  // Animation Loop
  function startLoop() {
    const trackCanvas = document.getElementById("trackCanvas");
    const trackCtx = trackCanvas.getContext("2d");

    const nurseryCanvas = document.getElementById("nurseryCanvas");
    const nurseryCtx = nurseryCanvas.getContext("2d");

    const startTime = performance.now();

    function loop(now) {
      const time = (now - startTime) / 1000;
      renderTrack(trackCtx, trackCanvas.width, trackCanvas.height, time);
      renderNursery(nurseryCtx, nurseryCanvas.width, nurseryCanvas.height, time);
      requestAnimationFrame(loop);
    }

    requestAnimationFrame(loop);
  }

  // Initialize
  async function init() {
    restoreState();
    await loadMilestones();
    setupEvents();
    updateUI();
    startLoop();
  }

  window.addEventListener("DOMContentLoaded", init);
})();
