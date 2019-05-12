const currentTime = () => {
    const now = new Date();
    const h = now.getHours();
    const min = now.getMinutes();
    const sec = now.getSeconds();
    return {h, min, sec};
};

// Set --hour, --minute and --second
const setHourMinuteSecond = (elt) => () => {
    const {h, min, sec} = currentTime();
    elt.style.setProperty("--hour", "" + h);
    elt.style.setProperty("--minute", "" + min);
    elt.style.setProperty("--second", "" + sec);
};

setInterval(setHourMinuteSecond(document.body), 1000 /* 1s */);