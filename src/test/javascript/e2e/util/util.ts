import { by, ElementFinder } from 'protractor';

export const addElementNgSelect = async (elementFinder: ElementFinder, label: string) => {
  await elementFinder.element(by.tagName('input')).sendKeys(label);
  await elementFinder
    .all(by.cssContainingText('.ng-option-label', label))
    .last()
    .click();
}
