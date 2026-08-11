from selenium import webdriver
from selenium.webdriver.chrome.options import Options

opts = Options()
opts.add_argument("--headless=new")
opts.add_argument("--no-sandbox")
opts.add_argument("--disable-gpu")
opts.add_argument("--window-size=1920,1080")

try:
    print("Attempting to create Chrome driver...")
    driver = webdriver.Chrome(options=opts)
    version = driver.capabilities.get("browserVersion", "unknown")
    print(f"Driver created! Browser Version: {version}")
    driver.get("about:blank")
    print(f"Page URL: {driver.current_url}")
    driver.quit()
    print("SUCCESS: Chrome driver works correctly!")
except Exception as e:
    print(f"FAILED: {type(e).__name__}: {e}")
