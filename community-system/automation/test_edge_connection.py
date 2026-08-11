import sys, os
sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from selenium import webdriver
from selenium.webdriver.edge.options import Options

opts = Options()
opts.add_argument("--headless=new")
opts.add_argument("--no-sandbox")
opts.add_argument("--disable-gpu")
opts.add_argument("--window-size=1920,1080")

try:
    print("Creating Edge driver...")
    driver = webdriver.Edge(options=opts)
    print(f"Edge driver created! Version: {driver.capabilities.get('browserVersion', 'unknown')}")
    
    url = "http://localhost:5173/login"
    print(f"Navigating to: {url}")
    driver.get(url)
    print(f"Page title: {driver.title}")
    print(f"Current URL: {driver.current_url}")
    
    if "login" in driver.current_url.lower() or "5173" in driver.current_url:
        print("SUCCESS: Application is accessible!")
    else:
        print(f"WARNING: Unexpected URL: {driver.current_url}")
    
    driver.quit()
    print("Edge driver closed. Test PASSED!")
except Exception as e:
    print(f"FAILED: {type(e).__name__}: {e}")
    import traceback
    traceback.print_exc()
