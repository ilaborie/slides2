const puppeteer = require('puppeteer');

const pdfOptions = {
    printBackground: true,
    format: 'A4'
}

const data = require('./slides2-kt/public/data.json');

async function generate() {
    const browser = await puppeteer.launch();
    const page = await browser.newPage();
    // console.log({data});
    for (const pres of data) {
        for (const instance of pres.instances) {
            // console.log({instance});
            const base = `slides2-kt/public/${instance.path}/index-${instance.label}`;
            const url = `http://localhost:1234/${instance.path}/index-${instance.label}.html`;
            console.log('🔗', url);
            await page.goto(url, {
                waitUntil: 'networkidle2'
            });
            await page.waitFor(10 * 1000); // 10s
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
        console.log('📸', path);
    } catch (e) {
        console.error(e);
    }
}

async function printPdf(page, base) {
    try {
        const path = `./${base}.pdf`;
        await page.pdf({
            ...pdfOptions,
            path
        });
        console.log('📇', path)
    } catch (e) {
        console.error(e);
    }
}

generate();