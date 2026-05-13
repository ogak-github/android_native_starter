# Project UI/UX Guidelines

## Design Principles

The application UI/UX should feel:
- professional
- modern
- clean
- efficient
- production-ready

Prioritize:
- clarity
- usability
- consistency
- fast interaction
- information density without clutter
- comfortable interaction for users with larger fingers/thumbs

Avoid:
- oversized UI elements
- excessive whitespace
- playful consumer-style design
- overly colorful layouts
- unnecessary animations
- inconsistent spacing
- tiny tap targets
- cramped interactive controls

---

# Touch & Interaction Standards

Design interactions assuming:
- users may operate the app one-handed
- users may have large thumbs/fingers
- fast tapping accuracy is important
- accidental taps must be minimized

Interactive elements must:
- remain compact visually
- still provide comfortable touch areas
- prioritize accessibility over ultra-dense layouts

Minimum recommended touch target:
- 44x44dp minimum
- 48x48dp preferred for primary interactions

Important:
- visual size and touch target size are NOT always the same
- compact UI is allowed as long as hit areas stay accessible

Use:
- additional internal padding
- invisible tap padding if needed
- proper spacing between adjacent actions

Avoid:
- tiny icon buttons
- tightly packed actions
- edge-to-edge clickable items without spacing
- small checkboxes/radio buttons
- difficult-to-tap overflow menus

---

# Visual Style

Preferred style:
- modern enterprise app
- dashboard-oriented
- minimal but not empty
- clean typography
- subtle elevation
- balanced spacing

Inspired by:
- Linear
- Notion
- Stripe Dashboard
- GitHub
- modern Android/iOS system UI

---

# Layout Rules

Use:
- compact spacing
- consistent padding
- clear visual hierarchy
- aligned components
- comfortable touch spacing between interactive elements

Preferred spacing scale:
- 4
- 8
- 12
- 16
- 20
- 24

Preferred interaction spacing:
- minimum 8dp between tappable controls
- 12-16dp preferred for primary actions

Avoid:
- random spacing values
- giant padding
- oversized cards
- excessive margins
- tightly stacked buttons
- accidental-touch-prone layouts

---

# Component Guidelines

## Buttons

Preferred:
- medium height
- clear hierarchy
- concise labels
- accessible touch area
- comfortable horizontal padding

Recommended:
- min height: 44dp
- preferred height: 48-52dp
- icon buttons: minimum 44x44dp tap area

Avoid:
- giant rounded buttons
- excessive shadows
- oversized CTA buttons
- tiny compact buttons
- icon-only buttons without enough tap space

Preferred labels:
- Save
- Continue
- Retry
- Update

Avoid:
- Click Here
- Submit Now
- Proceed Immediately

Primary buttons should:
- feel easy to tap quickly
- support one-handed use
- avoid accidental adjacent taps

---

## Cards

Cards should:
- have subtle elevation
- use compact padding
- group related content only
- maintain comfortable internal spacing

Avoid:
- huge empty cards
- nested cards
- card inside card patterns
- dense unreadable card content

If cards contain actions:
- keep action buttons properly spaced
- avoid tiny action icons
- maintain readable touch areas

---

## Dialogs

Dialogs should:
- be concise
- focus on one action
- avoid excessive text
- maintain comfortable button spacing

Avoid:
- fullscreen dialogs unless necessary
- too many actions
- cramped footer buttons

Dialog actions should:
- be easy to tap
- support mobile thumb interaction
- avoid stacked tiny actions

---

# Typography

Use:
- For this project use english for any wording
- clean readable typography
- consistent font scale
- medium font weight for emphasis

Avoid:
- excessive bold text
- too many font sizes
- decorative typography
- tiny unreadable captions

Preferred hierarchy:
- Title
- Subtitle
- Body
- Caption

Text should:
- remain readable on small phones
- maintain proper line height
- avoid dense unreadable paragraphs

---

# Color Usage

Use:
- limited color palette
- neutral surfaces
- primary color only for emphasis
- semantic colors for status

Avoid:
- rainbow UI
- saturated backgrounds
- random accent colors

Status colors:
- success
- warning
- error
- info

must stay consistent throughout app.

---

# Dark Mode

UI must support:
- dark mode
- light mode

Dark mode should:
- reduce eye strain
- avoid pure black
- maintain contrast readability

Interactive elements in dark mode should:
- maintain visible boundaries
- preserve tap clarity
- avoid low-contrast controls

---

# Responsive Design

UI should adapt properly for:
- phones
- tablets
- desktop if applicable

Avoid:
- fixed width layouts
- overflow-prone UI
- hardcoded dimensions

On phones:
- prioritize thumb reachability
- avoid top-heavy important actions
- maintain comfortable gesture zones

On tablets/desktop:
- avoid stretched giant controls
- preserve readable content width

---

# Forms

Forms should:
- feel lightweight
- minimize friction
- provide clear validation
- remain easy to fill one-handed

Use:
- inline validation
- proper keyboard type
- helpful placeholders
- sufficient field height
- comfortable spacing between inputs

Recommended:
- input height around 48-56dp
- sufficient vertical spacing between fields

Avoid:
- long overwhelming forms
- excessive required fields
- tiny dropdowns
- cramped input fields

---

# Lists & Tables

Lists should:
- be easy to scan
- use compact item spacing
- prioritize readability
- maintain touch-friendly row height

Recommended:
- list item min height around 48dp if interactive

Avoid:
- giant list items
- excessive divider usage
- visual clutter
- tiny tap targets inside rows

For row actions:
- avoid multiple tiny icons together
- maintain spacing between actions

---

# Loading States

Use:
- skeleton loading
- subtle progress indicators

Avoid:
- blocking entire screen unnecessarily
- infinite spinner without feedback

Loading states should:
- preserve layout stability
- avoid accidental taps during transitions

---

# Empty States

Empty states should:
- explain clearly
- provide next action
- stay visually minimal

Avoid:
- joke messages
- excessive illustration

Primary actions in empty states should:
- be obvious
- easy to tap
- centered within comfortable reach

---

# Error States

Error UI should:
- be clear
- actionable
- concise

Preferred:
- Retry button
- Short explanation

Avoid:
- raw backend errors
- technical stack trace

Error recovery actions must:
- remain easily tappable
- avoid tiny inline text actions

---

# Animations

Animations should:
- be subtle
- improve UX
- feel responsive

Use:
- fade
- small transitions
- smooth state updates

Avoid:
- flashy animation
- slow transitions
- animation overload

Animations must:
- never delay interaction
- preserve responsiveness
- not interfere with rapid tapping

---

# Navigation UX

Navigation should:
- minimize user steps
- maintain context
- feel predictable
- support thumb-friendly interaction

Avoid:
- deep confusing navigation
- inconsistent back behavior
- difficult-to-reach primary actions

Bottom navigation should:
- maintain comfortable touch targets
- avoid overcrowding
- use clear active states

---

# Accessibility

UI must:
- maintain readable contrast
- support dynamic text scaling
- provide accessible touch targets
- support comfortable thumb interaction

Minimum touch target:
- 44x44dp minimum
- 48x48dp preferred

Avoid:
- tiny clickable areas
- low contrast text
- cramped interactive elements
- relying on precision tapping

Accessibility should not be sacrificed for compact layouts.

---

# Professional UX Rules

Always prioritize:
1. usability
2. speed
3. clarity
4. consistency
5. comfortable interaction

UI should feel:
- trustworthy
- polished
- efficient
- modern

Avoid:
- experimental layouts
- trendy gimmicks
- unnecessary decoration
- precision-dependent interactions

---

# Assistant Instructions

When generating UI:
- keep layouts compact
- use modern spacing
- prioritize readability
- avoid oversized elements
- create production-ready interfaces
- maintain consistent hierarchy
- prefer clean enterprise-style design
- maintain accessible touch targets
- ensure buttons are easy to tap
- avoid tightly packed interactions

When generating Flutter widgets:
- avoid deeply nested widget trees
- split reusable components properly
- support dark mode
- use responsive layout patterns
- use proper constraints instead of hardcoded sizes
- ensure gesture targets remain accessible

When generating Android Compose UI:
- follow Material 3
- keep composables reusable
- maintain compact professional layout
