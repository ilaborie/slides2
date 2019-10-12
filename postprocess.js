const puppeteer = require('puppeteer');
const {Logger, LogLevel} = require('plop-logger');
const {colorEmojiConfig} = require('plop-logger/lib/extra/colorEmojiConfig');

Logger.config = colorEmojiConfig;
const logger = Logger.getLogger('postprocess');
logger.level = LogLevel.All;

const timeoutInSeconds = 5;
const pdfOptions = {
    printBackground: true,
    format: 'A4'
}

const data = require('./slides2-kt/public/data.json');

async function generate() {
    logger.info('start puppeteer');
    const browser = await puppeteer.launch();
    const page = await browser.newPage();
    // console.log({data});
    for (const pres of data) {
        for (const instance of pres.instances) {
            logger.info('handle', instance.path);
            // console.log({instance});
            const base = `slides2-kt/public/${instance.path}/index-${instance.label}`;
            const url = `http://localhost:1234/${instance.path}/index-${instance.label}.html`;
            logger.debug('url', url);
            await page.goto(url, {
                waitUntil: 'networkidle2'
            });
            await page.waitFor(timeoutInSeconds * 1000);
            await screenshot(page, base);
            await printPdf(page, base);
        }
    }
    await browser.close();
}

async function screenshot(page, base) {
    try {
        const path = `./${base}.png`;
        await page.screenshot({
            path
        });
        logger.info('📸', path);
    } catch (e) {
        logger.error(e);
    }
}

async function printPdf(page, base) {
    try {
        const path = `./${base}.pdf`;
        await page.pdf({
            ...pdfOptions,
            path
        });
        logger.info('📇', path)
    } catch (e) {
        logger.error(e);
    }
}

generate();