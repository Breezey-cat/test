from selenium import webdriver
from selenium.webdriver.edge.options import Options

opts = Options()
opts.add_argument("--headless=new")
opts.add_argument("--no-sandbox")
opts.add_argument("--disable-gpu")
opts.add_argument("--window-size=1920,1080")

try:
    print("Attempting to create Edge driver...")
    driver = webdriver.Edge(options=opts)
    version = driver.capabilities.get("browserVersion", "unknown")
    print(f"Driver created! Edge Version: {version}")
    driver.get("about:blank")
    print(f"Page URL: {driver.current_url}")
    driver.quit()
    print("SUCCESS: Edge driver works correctly!")
except Exception as e:
    print(f"Edge FAILED: {type(e).__name__}: {e}")

try:
    print("\nAttempting to create Chrome driver (fallback)...")
    from selenium.webdriver.chrome.options import Options as ChromeOptions
    copts = ChromeOptions()
    copts.add_argument("--headless=new")
    copts.add_argument("--no-sandbox")
    copts.add_argument("--disable-gpu")
    driver2 = webdriver.Chrome(options=copts)
    version2 = driver2.capabilities.get("browserVersion", "unknown")
    print(f"Chrome Version: {version2}")
    driver2.quit()
    print("SUCCESS: Chrome driver works!")
except Exception as e2:
    print(f"Chrome FAILED: {type(e2).__name__}: {e2}")

try:
    print("\nAttempting to create Firefox driver (fallback)...")
    from selenium.webdriver.firefox.options import Options as FirefoxOptions
    fopts = FirefoxOptions()
    fopts.add_argument("--headless")
    driver3 = webdriver.Firefox(options=fopts)
    version3 = driver3.capabilities.get("browserVersion", "unknown")
    print(f"Firefox Version: {version3}")
    driver3.quit()
    print("SUCCESS: Firefox driver works!")
except Exception as e3:
    print(f"Firefox FAILED: {type(e3).__name__}: {e3}")
