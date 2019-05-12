const currentTime = () => {
    const now = new Date();
    const h = now.getHours();
    const min = now.getMinutes();
    const sec = now.getSeconds();
    return { h, min, sec };
};

const { h, min, sec } = currentTime();
const delay = 60 * (min + 60 * h) + sec;

document.style.setProperty("--delay", "" + delay);