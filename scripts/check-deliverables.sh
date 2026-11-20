#!/usr/bin/env bash
# Checks the non-code deliverables. Exit 1 if anything is missing.
set -u
cd "$(dirname "$0")/.."
fail=0
say() { echo "  $1"; }
ng() { echo "FAIL: $1"; fail=1; }
ok() { echo "ok:   $1"; }

reg=docs/PATTERN_REGISTER.md
[ -f "$reg" ] || { ng "missing $reg"; exit 1; }

# register rows: lines starting with "| <number> |" excluding TODO rows
rows=$(grep -E '^\| *[0-9]+ *\|' "$reg" | grep -v 'TODO' | wc -l)
[ "$rows" -ge 8 ] && ok "pattern register has $rows filled rows (need >= 8)" || ng "pattern register has $rows filled rows (need >= 8)"

cats=0
for c in Creational Structural Behavioral; do
  grep -E '^\| *[0-9]+ *\|' "$reg" | grep -v TODO | grep -qi "$c" && cats=$((cats+1))
done
[ "$cats" -ge 3 ] && ok "all 3 categories mentioned" || ng "register must name the category (Creational/Structural/Behavioral) in >=1 row each (found $cats/3)"

rej=$(awk '/Rejected patterns/{f=1} f && /^\| *[A-Za-z]/ && !/Pattern *\|/ && !/TODO/ && !/---/' "$reg" | wc -l)
[ "$rej" -ge 2 ] && ok "$rej rejected patterns documented" || ng "need >= 2 rejected patterns (found $rej)"

adrs=$(ls docs/adr/[0-9][0-9][0-9][0-9]-*.md 2>/dev/null | grep -v 0000-template | wc -l)
[ "$adrs" -ge 5 ] && ok "$adrs ADRs" || ng "need >= 5 ADRs besides the template (found $adrs)"

refl=$(ls docs/reflection-*.md 2>/dev/null | wc -l)
[ "$refl" -ge 2 ] && ok "$refl reflections" || ng "need one reflection file per student (found $refl)"

grep -q '<PROJECT NAME>' README.md && ng "README still has the template placeholder" || ok "README customised"

tags=$(git tag 2>/dev/null | grep -c '^v')
[ "$tags" -ge 3 ] && ok "$tags release tags" || ng "need >= 3 release tags v0.x (found $tags)"

exit $fail
